package hu.ktk.it.dukashare.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime
import java.util.*

@Parcelize
data class Activity(
    val id: Long,
    val summary: String,
    val description: String,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val isU18: Boolean,
    val registrationDeadline: OffsetDateTime,
    val activityType: ActivityType,
    val activityState: ActivityState,
    val requiredParticipant: Int
        ): Parcelable{
}