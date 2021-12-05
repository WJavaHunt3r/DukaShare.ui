package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.ktk.it.dukashare.ActivityDetailHostActivity
import hu.ktk.it.dukashare.databinding.FragmentActivityDetailBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.service.ActivityService
import hu.ktk.it.dukashare.service.Utils
import java.time.format.DateTimeFormatter


class ActivityDetailFragment : Fragment() {

    private var selectedActivity: Activity? = null

    private lateinit var _binding: FragmentActivityDetailBinding

    private val binding get() = _binding!!
    private var activityId: Long = 0
    private val activityService = ActivityService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            activityId = args.getLong(ActivityDetailHostActivity.KEY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentActivityDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    private fun getActivity(id: Long) {
        activityService.getActivityById(id) {
            if (it != null) {
                selectedActivity = it
                setTexts()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivity(activityId)
    }

    private fun setTexts() {
        if (selectedActivity != null) {
            var arrow = "&#10132"
            var formatter = DateTimeFormatter.ofPattern(("hh:mm"))
            val startDate = Utils.convertStringToOffsetDateTime(selectedActivity!!.startDate!!)
            val endDate = Utils.convertStringToOffsetDateTime(selectedActivity!!.endDate!!)
            binding.tvActivityDate?.text = "${startDate.dayOfMonth}." +
                    "${startDate.month} " +
                    "${startDate.toLocalTime().format(formatter)} " +
                    "${Html.fromHtml(arrow)} ${endDate.toLocalTime().format(formatter)}"
            binding.tvActivitySummary?.text = selectedActivity?.summary
            binding.tvActivityDescription?.text = selectedActivity?.description
            binding.tvParticipants?.text = selectedActivity?.requiredParticipant.toString() +"/" + selectedActivity?.registrations!!.size
            binding.tvAvailablePlaces?.text =
                "${selectedActivity?.requiredParticipant!! - selectedActivity?.registrations!!.size} available places"
        }
    }
}