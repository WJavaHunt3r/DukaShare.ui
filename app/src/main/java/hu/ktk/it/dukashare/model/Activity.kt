package hu.ktk.it.dukashare.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime
import java.util.*

data class Activity(
    val id: Long,
    val summary: String?,
    val description: String?,
    val startDate: String?,
    val endDate: String?,
    val isU18: Boolean?,
    val registrationDeadline: String?,
    val activityTypeId: Long,
    val activityType: ActivityType?,
    val responsibleUserId: Long,
    val activityState: ActivityState?,
    val requiredParticipant: Int?,
    val registrations: List<Registration>?,
    val responsibleUser: Responsible?
        ){
}