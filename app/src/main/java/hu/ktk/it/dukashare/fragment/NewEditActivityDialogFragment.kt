package hu.ktk.it.dukashare.fragment


import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.DialogNewActivityBinding
import hu.ktk.it.dukashare.model.*
import hu.ktk.it.dukashare.service.ActivityTypeService
import hu.ktk.it.dukashare.service.UserService

class NewEditActivityDialogFragment : DialogFragment() {
    companion object {
        const val TAG = "NewEditActivityDialogFragment"
    }

    private lateinit var listener: NewEditActivityDialogListener

    private var _binding: DialogNewActivityBinding? = null
    private val binding get() = _binding!!
    private var activityTypeNames: List<String> = emptyList()
    private var userNames: List<String> = emptyList()
    private var activityTypes: List<ActivityType> = emptyList()
    private var users: List<User> = emptyList()
    private var activityTypeSelected: String = ""
    private var userSelected: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment: Fragment = requireParentFragment()

        listener = if (fragment is NewEditActivityDialogListener) {
            fragment
        } else {
            throw RuntimeException("Activity must implement the NewActivityDialogListener interface!")
        }
    }

    private fun isValid(): Boolean {
        try {
            binding.etRequiredParticipants.text.toString().toInt()
            return true
        } catch (e: NumberFormatException) {
            binding.etRequiredParticipants.error = "Enter a decimal number"
            return false
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogNewActivityBinding.inflate(layoutInflater)
        binding.activityTypeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                activityTypeSelected = activityTypeNames[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.responsibleUserSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                userSelected = userNames[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        getActivityTypes()
        getUsers()


        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.new_activity)
            .setView(binding.root)
            .setPositiveButton(R.string.save) { dialogInterface, i ->
                if (isValid()) {
                    listener.onActivityChanged(getNewActivity())
                }else{
                    dialogInterface.dismiss()
                }
            }
            .setNegativeButton(R.string.cancel, null).create()
    }

    private fun convertToStringDate(date: String, time: String): String {
        return date.replace('.', '-', true) + "T" + time + ":00"
    }

    private fun getNewActivity(): Activity {
        return Activity(
            summary = binding.etSummary.text.toString(),
            description = binding.etDescription.text.toString(),
            startDate = convertToStringDate(
                binding.etStartDate.text.toString(),
                binding.etStartTime.text.toString()
            ),
            endDate = convertToStringDate(
                binding.etEndDate.text.toString(),
                binding.etEndTime.text.toString()
            ),
            isU18 = binding.cbIsU18.isChecked,
            registrationDeadline = convertToStringDate(
                binding.etDeadLineDate.text.toString(),
                binding.etDeadLineTime.text.toString()
            ),
            activityTypeId = activityTypes[activityTypeNames.indexOf(activityTypeSelected)].id,
            responsibleUserId = users[userNames.indexOf(userSelected)].id,
            activityState = ActivityState.ONGOING,
            requiredParticipant = binding.etRequiredParticipants.text.toString().toInt(),
            responsibleUser = null,
            id = 0
        )
    }

    private fun getActivityTypes() {
        ActivityTypeService().getActivityTypes {
            if (it != null) {
                for (type in it) {
                    activityTypes = activityTypes + type!!
                    activityTypeNames = activityTypeNames + type.name
                }
                binding.activityTypeSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    activityTypeNames
                )
            }
        }
    }

    private fun getUsers() {
        UserService().getUsers {
            if (it != null) {
                for (user in it) {
                    if(user?.role == Role.MANAGER || user?.role == Role.ADMIN){
                        users = users + user
                        userNames = userNames + "${user.lastname} ${user.firstname}"
                    }
                }
                binding.responsibleUserSpinner.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    userNames
                )
            }
        }
    }

    interface NewEditActivityDialogListener {
        fun onActivityChanged(activity: Activity)
    }
}