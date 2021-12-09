package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.ActivityType
import retrofit2.Call
import retrofit2.http.GET

interface ActivityTypeAPI {
    @GET("ActivityType")
    fun getActivityTypes(): Call<List<ActivityType?>?>
}