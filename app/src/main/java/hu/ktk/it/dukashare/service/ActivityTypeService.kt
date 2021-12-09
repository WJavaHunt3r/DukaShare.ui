package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.model.ActivityType
import hu.ktk.it.dukashare.network.ActivityTypeAPI
import hu.ktk.it.dukashare.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityTypeService {
    private val retrofit = NetworkManager.buildService(ActivityTypeAPI::class.java)

    fun getActivityTypes(onResult: (List<ActivityType?>?) -> Unit) {
        retrofit.getActivityTypes().enqueue(object : Callback<List<ActivityType?>?> {
            override fun onResponse(
                call: Call<List<ActivityType?>?>,
                response: Response<List<ActivityType?>?>
            ) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<List<ActivityType?>?>, t: Throwable) {
                onResult(null)
            }

        })
    }
}