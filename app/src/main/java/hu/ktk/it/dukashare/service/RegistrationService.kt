package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.network.NetworkManager
import hu.ktk.it.dukashare.network.RegistrationAPI
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationService {
    private val retrofit = NetworkManager.buildService(RegistrationAPI::class.java)
    fun deleteRegistrationById(id: Long, onResult: (String) -> Unit) {
        retrofit.deleteRegistration(id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    onResult("successful")
                    Utils.updateUser()
                } else onResult("Failed")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onResult(t.localizedMessage!!)
            }

        })
    }

    fun addRegistration(registration: Registration, onResult: (Registration?) -> Unit) {
        retrofit.addRegistration(registration).enqueue(object : Callback<Registration?> {
            override fun onResponse(call: Call<Registration?>, response: Response<Registration?>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                    Utils.updateUser()
                }
            }

            override fun onFailure(call: Call<Registration?>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun getRegistrations(userId: Long?, activityId: Long?, onResult: (List<Registration?>?) -> Unit){
        retrofit.getRegistrations(userId, activityId).enqueue(object : Callback<List<Registration?>?> {
            override fun onResponse(call: Call<List<Registration?>?>, response: Response<List<Registration?>?>) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<List<Registration?>?>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun updateRegistration(id: Long, registration: Registration, onResult: (ResponseBody?) -> Unit) {
        retrofit.updateRegistration(id, registration).enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                onResult(null)
            }
        })
    }
}