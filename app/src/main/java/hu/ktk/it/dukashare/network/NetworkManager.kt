package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Activity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val BASE_URL = "localhost:8080/dukashare/api/"
    private const val UTF_8 = "UTF-8"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
    private val activityApi: ActivityAPI = retrofit.create(ActivityAPI::class.java);
    private val userApi: UserAPI = retrofit.create(UserAPI::class.java);

    fun getActivityById(id: Long): Call<Activity?>{
        return activityApi.getActivityById(id)
    }

    fun getActivities(): Call<List<Activity?>?>{
        return activityApi.getActivities()
    }

    fun getUserById(id: Long): Call<Activity?>{
        return userApi.getUserById(id)
    }

    fun getUsers(): Call<List<Activity?>?>{
        return userApi.getUsers()
    }
}