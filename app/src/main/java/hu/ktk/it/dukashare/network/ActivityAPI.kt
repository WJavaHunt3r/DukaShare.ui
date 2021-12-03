package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Activity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ActivityAPI {

    @GET("activities/{id}")
    fun getActivityById(@Path("id") id: Long): Call<Activity?>

    @GET("activities")
    fun getActivities(): Call<List<Activity?>?>


}