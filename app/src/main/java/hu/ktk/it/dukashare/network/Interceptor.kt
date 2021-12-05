package hu.ktk.it.dukashare.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Connection", "close")
            .build()
        return chain.proceed(request)
    }

}