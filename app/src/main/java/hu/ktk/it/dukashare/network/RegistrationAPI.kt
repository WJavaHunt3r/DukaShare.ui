package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {
    @POST("/registrations/{id}")
    fun addRegistration(@Body user: User) : Call<User>
}