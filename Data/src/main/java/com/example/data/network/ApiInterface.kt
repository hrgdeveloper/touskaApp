package com.example.data.network

import com.example.data.network.dtos.BlocDto
import com.example.data.network.utils.BaseResponse
import com.example.data.network.dtos.UserDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiInterface {
    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<UserDto>>


    @GET("bloc")
    suspend fun getBlocs(
    ): Response<BaseResponse<List<BlocDto>>>


    @POST("bloc")
    @FormUrlEncoded
    suspend fun createBLoc(
        @Field("name") name:String
    ): Response<BaseResponse<BlocDto>>

    @PUT("bloc/{id}")
    @FormUrlEncoded
    suspend fun updateBloc(
        @Field("name") name:String , @Path("id") id : Int
    ): Response<BaseResponse<BlocDto>>


    @DELETE("bloc/{id}")
    suspend fun deleteBloc( @Path("id") id : Int
    ): Response<BaseResponse<Any>>



}