package com.example.mycapstone.app

import android.widget.Toast
import com.example.mycapstone.model.postSignUpResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-ros2ig4wta-uc.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                val ApiService = retrofit.create(ApiService::class.java)
                val call = ApiService.register("Reza", "rezapalevie201@gmail.com","Pati","Abc12345","Abc12345")
                call.enqueue(object : Callback<postSignUpResponse>{
                    override fun onResponse(
                        call: Call<postSignUpResponse>,
                        response: Response<postSignUpResponse>
                    ) { if (response.isSuccessful){
                        val token = response.body()?.tokenBearer
                        val headers = mapOf("Authorization" to "Bearer $token")
                    }else{

                    }

                    }

                    override fun onFailure(call: Call<postSignUpResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            return retrofit.create(ApiService::class.java)
        }
    }
}