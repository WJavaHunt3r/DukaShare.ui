package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.DukaShare
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.adapter.HourRegRecycleViewAdapter
import hu.ktk.it.dukashare.databinding.FragmentActivityListBinding
import hu.ktk.it.dukashare.databinding.HourRegistrationBinding
import hu.ktk.it.dukashare.model.ActivityState
import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.service.ActivityService
import hu.ktk.it.dukashare.service.RegistrationService

class HourRegistrationFragment : Fragment(), HourRegRecycleViewAdapter.RegistrationOnChangedListener {
    private var _binding: HourRegistrationBinding? = null
    private val binding get() = _binding!!
    private var activityId: Long? = null
    private lateinit var registrationRecycleViewAdapter: HourRegRecycleViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { args ->
            activityId = args.getLong(DukaShare.ACTIVITY_ID)
        }
        _binding = HourRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        registrationRecycleViewAdapter = HourRegRecycleViewAdapter()
        registrationRecycleViewAdapter.itemClickListener = this
        registrationRecycleViewAdapter.activityId = activityId
        getActivities()
        binding.root.findViewById<RecyclerView>(R.id.registration_list).adapter=
            registrationRecycleViewAdapter
    }

    private fun getActivities() {
        RegistrationService().getRegistrations(null, activityId){
            if (it != null && it.isNotEmpty())
                for(reg in it) {
                    registrationRecycleViewAdapter.addItem(reg!!)
                }
            else Toast.makeText(
                activity,
                "Could not connect to server",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    override fun onRegistrationChanged(registration: Registration) {
        RegistrationService().updateRegistration(registration.id!!, registration){
            if (it == null){
                Toast.makeText(
                    requireActivity(),
                    "Network error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}