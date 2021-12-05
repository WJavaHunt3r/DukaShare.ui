package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.model.Activity
import hu.ktk.it.dukashare.network.ActivityAPI
import hu.ktk.it.dukashare.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityService {
    private val retrofit = NetworkManager.buildService(ActivityAPI::class.java)
    fun getActivityById(id: Long, onResult: (Activity?) -> Unit) {
        retrofit.getActivityById(id).enqueue(object : Callback<Activity?> {
            override fun onResponse(call: Call<Activity?>, response: Response<Activity?>) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<Activity?>, t: Throwable) {
                t.printStackTrace()
                onResult(null)

            }
        })
    }

    fun getActivities(onResult: (List<Activity?>?) -> Unit) {
        retrofit.getActivities().enqueue(object : Callback<List<Activity?>?> {
            override fun onResponse(
                call: Call<List<Activity?>?>, response: Response<List<Activity?>?>) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<List<Activity?>?>, t: Throwable) {
                t.printStackTrace()
                onResult(null)

            }

        })
    }
}