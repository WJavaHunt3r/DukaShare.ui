package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Activity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ActivityAPI {
    @GET("api/activity/{id}")
    fun getActivityById(@Path("id") id: Long): Call<Activity>

    @GET("api/Activity")
    fun getActivities(): Call<List<Activity?>?>


}