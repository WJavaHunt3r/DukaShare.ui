package hu.ktk.it.dukashare.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.DukaShare
import hu.ktk.it.dukashare.databinding.ManagerActivitiesLayoutBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.service.ActivityService

class ManagerActivities: ListFragment() {
    private lateinit var binding: ManagerActivitiesLayoutBinding
    private var activities: List<Activity?> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = ManagerActivitiesLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivities()
    }

    private fun getActivities(){
        var activityNames: List<String> = emptyList()
        ActivityService().getActivities {
            if (it != null){
                for(act in it){
                    if(act?.responsibleUserId == ApplicationContext.user?.id){
                        activities = activities + act
                        activityNames += act?.summary!!
                        listAdapter = ArrayAdapter<String>(activity?.baseContext!!, R.layout.simple_spinner_dropdown_item, activityNames)
                    }
                }
            }
        }
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val bundle = Bundle()
        bundle.putLong(
            DukaShare.ACTIVITY_ID,
            activities[position]?.id!!
        )

        NavHostFragment.findNavController(this).navigate(hu.ktk.it.dukashare.R.id.show_hour_registrations)
    }
}