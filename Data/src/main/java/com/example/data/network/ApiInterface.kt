package com.example.data.network

import com.example.data.network.utils.BaseResponse
import com.example.data.network.dtos.UserDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<UserDto>>

}