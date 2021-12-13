package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Activity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityAPI {
    @GET("Activity/{id}")
    fun getActivityById(@Path("id") id: Long): Call<Activity>

    @GET("Activity")
    fun getActivities(): Call<List<Activity?>?>

    @POST("Activity")
    fun addActivity(@Body activity: Activity) : Call<Activity>

}