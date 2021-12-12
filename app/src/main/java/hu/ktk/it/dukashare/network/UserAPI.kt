package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("User/{id}")
    fun getUserById(@Path("id") id: Long): Call<User>

    @GET("User")
    fun getUsers(): Call<List<User?>?>
}