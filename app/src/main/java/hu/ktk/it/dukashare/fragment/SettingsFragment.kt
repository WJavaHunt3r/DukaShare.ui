package hu.ktk.it.dukashare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import hu.ktk.it.dukashare.ApplicationContext
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.FragmentSettingsBinding
import hu.ktk.it.dukashare.model.Role

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvUserName.text = getString(R.string.user_name,ApplicationContext.user?.lastname, ApplicationContext.user?.firstname)

        if(ApplicationContext.user?.role == Role.GUI) binding.tvAdministration.isEnabled = false

        binding.tvAdministration.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.show_manager_activities)
        }
    }


}