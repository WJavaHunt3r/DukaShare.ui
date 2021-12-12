package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.ListFragment
import hu.ktk.it.dukashare.DukaShare
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.RegistrationsLayoutBinding
import hu.ktk.it.dukashare.network.filter.RegistrationFilter
import hu.ktk.it.dukashare.service.RegistrationService
import hu.ktk.it.dukashare.service.UserService

class RegistrationsFragment : ListFragment() {
    private lateinit var binding: RegistrationsLayoutBinding
    private var activityId: Long? = null
    private var names: List<String> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            activityId = args.getLong(DukaShare.ACTIVITY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = RegistrationsLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRegistrations()
    }

    private fun getRegistrations() {
        RegistrationService().getRegistrations(
            RegistrationFilter(
                activityId = activityId,
                userId = null
            )
        ) { it ->
            if (it != null) {
                var userIds: List<Long> = emptyList()
                for (reg in it) {
                    userIds = userIds + (reg?.userId!!)
                }
                getUsers(userIds)

            }
        }
    }

    private fun getUsers(userIds: List<Long>) {
        for (id in userIds) {
            UserService().getUserById(id) {
                if (it != null) names = names + "${it.lastname} ${it.surename}"
                listAdapter = ArrayAdapter<String>(activity?.baseContext!!, R.layout.simple_registration_layout, names)
            }
        }

    }
}