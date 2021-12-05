package hu.ktk.it.dukashare.adapter

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ktk.it.dukashare.databinding.SingleActivityBinding
import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.service.Utils
import java.time.format.DateTimeFormatter

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
        var arrow = "&#10132"
        var formatter = DateTimeFormatter.ofPattern(("hh:mm"))
        val startDate = Utils.convertStringToOffsetDateTime(activity.startDate!!)
        val endDate = Utils.convertStringToOffsetDateTime(activity.endDate!!)
        holder.binding.tvDate.text = "${startDate.dayOfWeek} ${startDate.dayOfMonth}.${startDate.month}"
        holder.binding.tvSummary.text = activity.summary
        holder.binding.tvTime.text =
            "${startDate.toLocalTime().format(formatter)} " +
                    "${Html.fromHtml(arrow)} ${endDate.toLocalTime().format(formatter)}"
        holder.binding.tvRegistered.text = "${activity.requiredParticipant!!}/${activity?.registrations!!.size}"


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