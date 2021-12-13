package hu.ktk.it.dukashare.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.DukaShare
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.FragmentActivityDetailBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.service.ActivityService
import hu.ktk.it.dukashare.service.RegistrationService
import hu.ktk.it.dukashare.service.Utils
import java.time.format.DateTimeFormatter


class ActivityDetailFragment : Fragment() {
    private var selectedActivity: Activity? = null

    private lateinit var _binding: FragmentActivityDetailBinding
    private var isRegistered: Boolean = false
    private val binding get() = _binding
    private var activityId: Long = 0
    private val activityService = ActivityService()
    private var regs: List<Registration> =  emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            activityId = args.getLong(DukaShare.ACTIVITY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getActivity(id: Long) {
        activityService.getActivityById(id) { it ->
            if (it != null) {
                selectedActivity = it
                RegistrationService().getRegistrations(null, activityId){
                    if(it != null){
                        regs = it as List<Registration>
                        isRegistered = Utils.isUserRegistered(regs)
                        setTexts()
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity(activityId)

        binding.btnRegister?.setOnClickListener {
            if (isRegistered) unregister()
            else register()
        }

        binding.btnMail?.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            var addresses = arrayOf(selectedActivity?.responsibleUser?.email!!)
            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, selectedActivity?.summary)

            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }

        binding.btnSeeRegistered?.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong(
                DukaShare.ACTIVITY_ID,
                activityId
            )
            NavHostFragment.findNavController(this).navigate(R.id.show_registrations, bundle)
        }
    }

    private fun unregister() {
        RegistrationService().getRegistrations(ApplicationContext.user?.id, activityId){ it ->
            if (it != null) {
                RegistrationService().deleteRegistrationById(it[0]?.id!!) {
                    if (it == "successful") {
                        isRegistered = false
                        refreshView()
                    }
                }
            }
        }

    }

    private fun refreshView() {
        getActivity(selectedActivity?.id!!)
        setTexts()
    }

    private fun register() {
        RegistrationService().addRegistration(
            Registration(
                null,
                ApplicationContext.user?.id!!,
                activityId,
                null,
                0.0
            )
        ) {
            if (it == null)
                Toast.makeText(
                    activity,
                    "Could not connect to server",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                isRegistered = true
                refreshView()
            }
        }
    }

    private fun setTexts() {
        if (selectedActivity != null) {
            val arrow = "&#10132"
            val formatter = DateTimeFormatter.ofPattern(("hh:mm"))
            val startDate = Utils.convertStringToOffsetDateTime(selectedActivity!!.startDate!!)
            val endDate = Utils.convertStringToOffsetDateTime(selectedActivity!!.endDate!!)
            val availablePlaces: Int =
                selectedActivity?.requiredParticipant!! - regs.size
            binding.tvActivityDate?.text = getString(
                R.string.activity_detail_date,
                startDate.dayOfMonth,
                startDate.month,
                startDate.toLocalTime().format(formatter),
                Html.fromHtml(arrow),
                endDate.toLocalTime().format(formatter)
            )

            binding.tvActivitySummary?.text = selectedActivity?.summary
            binding.tvActivityDescription?.text = selectedActivity?.description

            binding.tvParticipants?.text = getString(
                R.string.registered_slash_places,
                regs.size,
                selectedActivity?.requiredParticipant
            )

            binding.tvAvailablePlaces?.text = getString(
                R.string.available_places,
                availablePlaces
            )

            binding.tvResponsible?.text = getString(
                R.string.responsible,
                selectedActivity?.responsibleUser?.lastname,
                selectedActivity?.responsibleUser?.firstname
            )

            if (availablePlaces == 0) {
                binding.tvParticipants?.setTextColor(resources.getColor(R.color.fullColor))
                binding.tvParticipants?.text = getString(R.string.full)
                binding.tvAvailablePlaces?.text = getString(R.string.no_available_places)
                if (!isRegistered) binding.btnRegister?.isEnabled = false
            } else {
                binding.tvParticipants?.setTextColor(resources.getColor(R.color.lightGrey))
                binding.btnRegister?.isEnabled = true
            }

            binding.btnSeeRegistered?.isVisible =
                availablePlaces != selectedActivity?.requiredParticipant

            if (isRegistered) {
                binding.btnRegister?.text = getString(R.string.unregister)
            } else {
                binding.btnRegister?.text = getString(R.string.register)
            }
        }
    }
}