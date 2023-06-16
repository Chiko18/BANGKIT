package com.example.mycapstone.model

import com.google.gson.annotations.SerializedName

data class postSignUpResponse(
    @field: SerializedName("token")
    val tokenBearer: String? = null,
    @field: SerializedName("token")
    val tokenBearerlogin: String? = null
)
