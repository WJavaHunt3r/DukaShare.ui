package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.FragmentStatusBinding
import hu.ktk.it.dukashare.service.ActivityService
import hu.ktk.it.dukashare.service.RegistrationService
import hu.ktk.it.dukashare.service.UserService

class StatusFragment : Fragment() {
    private var _binding: FragmentStatusBinding? = null
    private var activityNames: List<String> = emptyList()
    private val binding get() = _binding!!
    private var simpleList: ListView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentStatusBinding.inflate(inflater, container, false)
        simpleList = binding.elvClosedRegistrations

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserStatus()
        getRegistrations()
    }

    private fun getUserStatus() {
        UserService().getUserStatus(ApplicationContext.user?.id!!) {
            if (it != null) binding.tvHours.text = getString(R.string.hours, it.toString())
        }
    }

    private fun getRegistrations() {
        RegistrationService().getRegistrations(ApplicationContext.user?.id, null) { it ->
            if (it != null) {
                for (reg in it) {
                    ActivityService().getActivityById(reg?.activityId!!) {
                        activityNames = activityNames + "${it?.summary!!}   (${reg.hours} hours)"
                        var adapter = ArrayAdapter<String>(activity?.baseContext!!, android.R.layout.simple_spinner_dropdown_item, activityNames)
                        simpleList!!.adapter = adapter
                    }
                }
            }
        }
    }


}