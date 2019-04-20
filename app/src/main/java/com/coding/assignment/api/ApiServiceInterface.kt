package com.coding.assignment.api


import com.coding.assignment.models.User
import com.coding.assignment.models.Users
import com.coding.assignment.util.Constants
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceInterface {

    @GET("users")
    fun getUsersList(@Query("page") page: Int, @Query("per_page") perPage : Int): Observable<Users>

    @POST("users")
    fun createUser(@Body user : HashMap<String, String> ): Observable<User>

    @PUT("users/{id}")
    fun updateItem(@Path("id") itemId: String, @Body user: User): Observable<User>

    companion object Factory {
        fun create(): ApiServiceInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}