package hu.ktk.it.dukashare.model

data class Activity(
    val id: Long,
    val summary: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val isU18: Boolean,
    val registrationDeadline: String,
    val activityTypeId: Long,
    val responsibleUserId: Long,
    val activityState: ActivityState?,
    val requiredParticipant: Int,
    val responsibleUser: Responsible?
)