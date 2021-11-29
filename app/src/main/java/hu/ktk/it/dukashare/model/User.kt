package hu.ktk.it.dukashare.model

import java.util.*

data class User (
    val id: Long,
    val surname: String,
    val lastname: String,
    val dateOfBirth: Date,
    val spouseId: Long,
    val email: String,


){
}