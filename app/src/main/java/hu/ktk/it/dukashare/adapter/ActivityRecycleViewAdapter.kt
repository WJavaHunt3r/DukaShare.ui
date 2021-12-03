package hu.ktk.it.dukashare.adapter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.databinding.SingleActivityBinding
import hu.ktk.it.dukashare.model.Activity

class ActivityRecycleViewAdapter() : ListAdapter<Activity, ActivityRecycleViewAdapter.ViewHolder>(itemCallback){
    companion object{
        object itemCallback : DiffUtil.ItemCallback<Activity>(){
            override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
                return oldItem == newItem
            }
        }
    }
    private var activityList = emptyList<Activity>()

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
        activityList += activity
        submitList(activityList)
    }

    fun addAll(activities: List<Activity>) {
        activityList += activities
        submitList(activityList)
    }

    fun deleteRow(position: Int) {
        activityList = activityList.filterIndexed{index, _ -> index != position}
        submitList(activityList)
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