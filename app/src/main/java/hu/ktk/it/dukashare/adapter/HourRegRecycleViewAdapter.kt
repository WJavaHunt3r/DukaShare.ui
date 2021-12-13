package hu.ktk.it.dukashare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.databinding.SimpleRegistrationLayoutBinding
import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.service.UserService

class HourRegRecycleViewAdapter :
    ListAdapter<Registration, HourRegRecycleViewAdapter.ViewHolder>(itemCallback) {
    companion object {
        object itemCallback : DiffUtil.ItemCallback<Registration>() {
            override fun areItemsTheSame(oldItem: Registration, newItem: Registration): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Registration, newItem: Registration): Boolean {
                return oldItem == newItem
            }
        }
    }
    var activityId: Long? = null
    var itemClickListener: RegistrationOnChangedListener? = null
    private var registrationList = emptyList<Registration>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            SimpleRegistrationLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var registration = registrationList[position]
        UserService().getUserById(registration.userId){
            holder.binding.tvName.text = it?.lastname + " " +it?.firstname

        }
    }

    fun addItem(registration: Registration) {
        registrationList = registrationList + registration
        submitList(registrationList)
    }

    inner class ViewHolder(val binding: SimpleRegistrationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var registration: Registration? = null

        init {
            itemView.setOnClickListener {
                registration?.let { registration ->
                    itemClickListener?.onRegistrationChanged(
                        registration
                    )
                }
            }
        }
    }

    interface RegistrationOnChangedListener {
        fun onRegistrationChanged(registration: Registration)
    }


}