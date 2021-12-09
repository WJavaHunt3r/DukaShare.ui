package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.DukaShare
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.adapter.ActivityRecycleViewAdapter
import hu.ktk.it.dukashare.databinding.FragmentActivityListBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.model.ActivityState
import hu.ktk.it.dukashare.service.ActivityService


class ActivityListFragment : Fragment(), ActivityRecycleViewAdapter.ActivityClickListener {
    private var _binding: FragmentActivityListBinding? = null
    private var activityDetailFragmentContainer: View? = null
    private val binding get() = _binding!!
    private var activityService: ActivityService = ActivityService()
    private lateinit var activityRecyclerViewAdapter: ActivityRecycleViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentActivityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityDetailFragmentContainer = view.findViewById(R.id.activity_detail_nav_container)
        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        activityRecyclerViewAdapter = ActivityRecycleViewAdapter()
        activityRecyclerViewAdapter.itemClickListener = this
        getActivities()
        binding.root.findViewById<RecyclerView>(R.id.activity_list).adapter =
            activityRecyclerViewAdapter
    }

    private fun getActivities() {
        activityService.getActivities {
            if (it != null) {
                for (act in it) {
                    if (act?.activityState == ActivityState.ONGOING)
                        activityRecyclerViewAdapter.addItem(act)
                }
                binding.tvActivityCount?.text =
                    getString(R.string.activity_count, ApplicationContext.user?.registrations?.size)
            } else Toast.makeText(
                activity,
                "Could not connect to server",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onItemClick(activity: Activity) {
        val bundle = Bundle()
        bundle.putLong(
            DukaShare.KEY_ID,
            activity.id
        )
        if (activityDetailFragmentContainer != null) {

            activityDetailFragmentContainer!!.findNavController()
                .navigate(R.id.fragment_activity_detail, bundle)
        } else {
            findNavController(this).navigate(R.id.show_activity_detail, bundle)
        }
    }
}