package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Login
import hu.ktk.it.dukashare.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("Login")
    fun login(@Body login: Login): Call<User?>
}