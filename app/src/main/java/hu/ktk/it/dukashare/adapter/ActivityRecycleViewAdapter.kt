package hu.ktk.it.dukashare.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.R
import hu.ktk.it.dukashare.databinding.SingleActivityBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.service.Utils
import java.time.format.DateTimeFormatter

class ActivityRecycleViewAdapter :
    ListAdapter<Activity, ActivityRecycleViewAdapter.ViewHolder>(itemCallback) {
    companion object {
        object itemCallback : DiffUtil.ItemCallback<Activity>() {
            override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var activityList = emptyList<Activity>()
    private var registeredActivity: Int = 0
    var itemClickListener: ActivityClickListener? = null
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            SingleActivityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activityList[position]
        val regs: List<Registration> = activity.registrations!!
        if (Utils.isUserRegistered(regs)) {
            registeredActivity += 1
            holder.binding.activityDetailLayout.setBackgroundResource(R.drawable.registered_background_green)
        }

        holder.activity = activity
        val arrow = "&#10132"
        val formatter = DateTimeFormatter.ofPattern(("hh:mm"))
        val startDate = Utils.convertStringToOffsetDateTime(activity.startDate!!)
        val endDate = Utils.convertStringToOffsetDateTime(activity.endDate!!)
        holder.binding.tvDate.text = context.getString(
            R.string.activity_date,
            startDate.dayOfWeek,
            startDate.dayOfMonth,
            startDate.month
        )
        holder.binding.tvSummary.text = activity.summary
        holder.binding.tvTime.text = context.getString(
            R.string.activity_time,
            startDate.toLocalTime().format(formatter),
            Html.fromHtml(arrow),
            endDate.toLocalTime().format(formatter)
        )
        holder.binding.tvRegistered.text = context.getString(
            R.string.registered_slash_places,
            activity.registrations.size,
            activity.requiredParticipant!!
        )
        val availablePlaces: Int = activity.requiredParticipant - activity.registrations.size
        if(availablePlaces == 0){
            holder.binding.tvRegistered.setTextColor(context.resources.getColor(R.color.fullColor))
            holder.binding.tvRegistered.text = context.getString(R.string.full)
        }
    }

    fun addItem(activity: Activity) {
        activityList = activityList + activity
        submitList(activityList)
    }

    fun deleteAll(){
        activityList = emptyList<Activity>()
    }

    inner class ViewHolder(val binding: SingleActivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var activity: Activity? = null

        init {
            itemView.setOnClickListener {
                activity?.let { activity -> itemClickListener?.onItemClick(activity) }
            }
        }
    }

    interface ActivityClickListener {
        fun onItemClick(activity: Activity)
    }
}