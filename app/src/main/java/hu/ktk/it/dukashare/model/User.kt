package hu.ktk.it.dukashare.model

data class User(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val dateOfBirth: String,
    val spouseId: Long,
    val email: String,
    val role: Role
)