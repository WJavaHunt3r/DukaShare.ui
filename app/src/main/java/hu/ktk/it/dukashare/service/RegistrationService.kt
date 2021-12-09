package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.ApplicationContext
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

    fun findUserActivityRegistration(activityId: Long): Registration? {
        for (reg in ApplicationContext.user?.registrations!!) {
            if (reg.activityId == activityId) {
                return reg
            }
        }
        return null
    }
}