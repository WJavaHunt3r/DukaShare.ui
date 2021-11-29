package hu.ktk.it.dukashare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.databinding.SingleActivityBinding
import hu.ktk.it.dukashare.model.Activity

class ActivityRecycleViewAdapter : RecyclerView.Adapter<ActivityRecycleViewAdapter.ViewHolder>() {

    private val activityList = mutableListOf<Activity>()

    var itemClickListener: ActivityClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SingleActivityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activityList[position]

        holder.activity = activity

        holder.binding.tvDate.text = "${activity.startDate?.dayOfWeek} ${activity.startDate?.dayOfMonth}.${activity.startDate?.month}"
        holder.binding.tvSummary.text = activity.summary
        holder.binding.tvTime.text =
            activity.startDate.hour.toString() + "->" + activity.endDate.hour.toString()
        holder.binding.tvRegistered.text = "10/" + activity.requiredParticipant


    }

    fun addItem(activity: Activity) {
        val size = activityList.size
        activityList.add(activity)
        notifyItemInserted(size)
    }

    fun addAll(activities: List<Activity>) {
        val size = activityList.size
        activityList += activities
        notifyItemRangeInserted(size, activities.size)
    }

    fun deleteRow(position: Int) {
        activityList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = activityList.size

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