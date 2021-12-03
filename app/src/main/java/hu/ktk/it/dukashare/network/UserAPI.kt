package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Activity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Long): Call<Activity?>

    @GET("users")
    fun getUsers(): Call<List<Activity?>?>
}