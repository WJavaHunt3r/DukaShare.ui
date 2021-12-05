package hu.ktk.it.dukashare.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private const val BASE_URL = "http://10.0.2.2:5000/"
    private const val UTF_8 = "UTF-8"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor())
    }.build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }

}