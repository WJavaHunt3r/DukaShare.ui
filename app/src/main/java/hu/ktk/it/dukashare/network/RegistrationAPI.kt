package hu.ktk.it.dukashare.network

import hu.ktk.it.dukashare.model.Registration
import hu.ktk.it.dukashare.network.filter.RegistrationFilter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RegistrationAPI {
    @POST("Registration")
    fun addRegistration(@Body registration: Registration): Call<Registration?>

    @DELETE("Registration/{id}")
    fun deleteRegistration(@Path("id") id: Long): Call<ResponseBody>

    @GET("Registration")
    fun getRegistrations(@Query ("userId") userId: Long?,
                         @Query ("activityId") activityId: Long?): Call<List<Registration?>?>
}