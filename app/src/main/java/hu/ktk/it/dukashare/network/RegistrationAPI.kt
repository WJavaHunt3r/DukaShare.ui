package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Registration
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface RegistrationAPI {
    @POST("Registration")
    fun addRegistration(@Body registration: Registration): Call<Registration?>

    @DELETE("Registration/{id}")
    fun deleteRegistration(@Path("id") id: Long): Call<ResponseBody>
}