package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.model.User
import hu.ktk.it.dukashare.network.NetworkManager.buildService
import hu.ktk.it.dukashare.network.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserService {
    private val retrofit = buildService(UserAPI::class.java)
    fun getUserById(id: Long, onResult: (User?) -> Unit) {
        retrofit.getUserById(id).enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getUsers(onResult: (List<User?>?) -> Unit) {
        retrofit.getUsers().enqueue(object : Callback<List<User?>?> {
            override fun onResponse(
                call: Call<List<User?>?>, response: Response<List<User?>?>
            ) {
                onResult(response.body()!!)
            }

            override fun onFailure(call: Call<List<User?>?>, t: Throwable) {
                onResult(null)
            }

        })
    }
}