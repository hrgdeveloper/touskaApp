package com.example.data.network

import com.example.data.network.dtos.BlocDto
import com.example.data.network.dtos.FloorDto
import com.example.data.network.dtos.UnitDto
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
import retrofit2.http.Query

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



    @GET("floor")
    suspend fun getFloors(
        @Query("bloc_id") bloc_id:Int
    ): Response<BaseResponse<List<FloorDto>>>


    @POST("floor")
    @FormUrlEncoded
    suspend fun createFloor(
        @Field("name") name:String,
        @Field("number") number:Int,
        @Field("bloc_id") bloc_id:Int
    ): Response<BaseResponse<FloorDto>>


    @PUT("floor/{id}")
    @FormUrlEncoded
    suspend fun updateFloor(
        @Field("name") name:String,
        @Field("number") number:Int,
        @Path("id") floor_id:Int
    ): Response<BaseResponse<FloorDto>>

    @DELETE("floor/{id}")
    suspend fun deleteFloor( @Path("id") id : Int
    ): Response<BaseResponse<Any>>



    @GET("unit")
    suspend fun getUnits(
        @Query("floor_id") floor_id:Int
    ): Response<BaseResponse<List<UnitDto>>>



    @POST("unit")
    @FormUrlEncoded
    suspend fun createUnit(
        @Field("name") name:String,
        @Field("floor_id") floor_id:Int
    ): Response<BaseResponse<UnitDto>>


    @PUT("unit/{id}")
    @FormUrlEncoded
    suspend fun updateUnit(
        @Field("name") name:String,
        @Path("id") floor_id:Int
    ): Response<BaseResponse<UnitDto>>

    @DELETE("unit/{id}")
    suspend fun deleteUnit( @Path("id") id : Int
    ): Response<BaseResponse<Any>>


}