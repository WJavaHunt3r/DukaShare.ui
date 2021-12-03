package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.ktk.it.dukashare.ActivityDetailHostActivity
import hu.ktk.it.dukashare.databinding.FragmentActivityDetailBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.service.ActivityService


class ActivityDetailFragment : Fragment() {

    private var selectedActivity: Activity? = null

    private lateinit var _binding: FragmentActivityDetailBinding

    private val binding get() = _binding!!

    private val activityService = ActivityService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            getActivity(args.getLong(ActivityDetailHostActivity.KEY_ID))
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
            if (it != null) selectedActivity = it
            else {
                Toast.makeText(
                    ActivityDetailHostActivity(),
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arrow = "&#10132"
        binding.tvActivityDate?.text = "${selectedActivity?.startDate?.dayOfMonth}." +
                "${selectedActivity?.startDate?.month} " +
                "${selectedActivity?.startDate?.hour}.${selectedActivity?.startDate?.minute} " +
                "${Html.fromHtml(arrow)} ${selectedActivity?.endDate?.hour}.${selectedActivity?.endDate?.minute}"
        binding.tvActivitySummary?.text = selectedActivity?.summary
        binding.tvActivityDescription?.text = selectedActivity?.description
        binding.tvParticipants?.text = "10/" + selectedActivity?.requiredParticipant
        binding.tvAvailablePlaces?.text =
            "${selectedActivity?.requiredParticipant} available places"
    }
}