package com.example.mycapstone.app

import com.example.mycapstone.model.postSignUpResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<postSignUpResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String

    ): Call<postSignUpResponse>
}