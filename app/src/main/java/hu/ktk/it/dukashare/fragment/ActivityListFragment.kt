package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.ActivityDetailHostActivity
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.adapter.ActivityRecycleViewAdapter
import hu.ktk.it.dukashare.databinding.FragmentActivityListBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.model.ActivityState
import hu.ktk.it.dukashare.model.ActivityType
import java.time.OffsetDateTime


class ActivityListFragment : Fragment(), ActivityRecycleViewAdapter.ActivityClickListener {



    private var _binding: FragmentActivityListBinding? = null
    private  var activityDetailFragmentContainer: View? = null
    private val binding get() = _binding!!

    private lateinit var activityRecyclerViewAdapter: ActivityRecycleViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentActivityListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityDetailFragmentContainer = view.findViewById(R.id.activity_detail_nav_container)
        setUpRecycleView()

    }

    private fun setUpRecycleView(){
        val data = mutableListOf(
            Activity(1, "Takarítás Dukán", "Takarítás területen dukán", OffsetDateTime.now(), OffsetDateTime.now(), false, OffsetDateTime.now(), ActivityType(0, "Takarítás"), ActivityState.ONGOING, 12 ),
            Activity(2, "Mosogatás Dukán", "Mosogatás esküvő után ", OffsetDateTime.now(), OffsetDateTime.now(), true, OffsetDateTime.now(), ActivityType(1, "Mosogatás"), ActivityState.ONGOING, 15 ),
            Activity(3, "Fűkaszálás Dányban", "Fűkaszálás Dány sűlysáp területen ", OffsetDateTime.now(), OffsetDateTime.now(), true, OffsetDateTime.now(), ActivityType(2, "Kaszálás"), ActivityState.OVER, 20 ),
        )
        activityRecyclerViewAdapter = ActivityRecycleViewAdapter()
        activityRecyclerViewAdapter.itemClickListener = this
        activityRecyclerViewAdapter.addAll(data)
        binding.root.findViewById<RecyclerView>(R.id.activity_list).adapter = activityRecyclerViewAdapter
    }

    override fun onItemClick(activity: Activity) {
        val bundle = Bundle()
        bundle.putParcelable(
            ActivityDetailHostActivity.KEY_ID,
            activity
        )
        if (activityDetailFragmentContainer != null) {
            activityDetailFragmentContainer!!.findNavController()
                .navigate(R.id.fragment_activity_detail, bundle)
        } else {
            findNavController(this).navigate(R.id.show_activity_detail, bundle)
        }
    }
}