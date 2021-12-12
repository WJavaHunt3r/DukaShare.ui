package hu.ktk.it.dukashare.service

import hu.ktk.it.dukashare.model.Login
import hu.ktk.it.dukashare.model.User
import hu.ktk.it.dukashare.network.LoginAPI
import hu.ktk.it.dukashare.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService {
    private val retrofit = NetworkManager.buildService(LoginAPI::class.java)
    fun loginUser(login: Login, onResult: (User?) -> Unit) {
        retrofit.login(login).enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.isSuccessful)
                    onResult(response.body()!!)
                else onResult(null)
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                onResult(null)
            }
        })
    }
}