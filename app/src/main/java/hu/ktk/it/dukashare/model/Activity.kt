package hu.ktk.it.dukashare.model

import java.util.*

data class Activity (
    val id: Long,
    val summary: String,
    val description: String,
    val startDate: Date,
    val endDate: Date,
    val isU18: Boolean,
    val registrationDeadline: Date,
    val activityType: ActivityType,
    val registrations: List<Registration>
        ){

}