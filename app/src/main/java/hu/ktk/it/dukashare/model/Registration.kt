package hu.ktk.it.dukashare.model

import java.util.*

data class Registration(
    val id: Long,
    val userId: String,
    val activityId: Long,
    val dateOfRegistration: Date

) {
}