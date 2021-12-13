package hu.ktk.it.dukashare.model

data class User(
    val id: Long,
    val surename: String,
    val lastname: String,
    val dateOfBirth: String,
    val spouseId: Long,
    val email: String,
    val registrations: List<Registration>,
    val role: Role
)