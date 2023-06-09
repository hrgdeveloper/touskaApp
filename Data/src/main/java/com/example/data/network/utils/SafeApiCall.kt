package com.example.data.network.utils

import android.util.Log
import com.example.data.BuildConfig
import com.example.shared.Resource
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeApiCall {
    inline  fun <T>  safeCall (call :  ()->Response<BaseResponse<T>>) : T{
           try {
               val result = call()
               if (result.isSuccessful) {
                if (result.body()!=null) {
                    return result.body()!!.data
                }else {
                    return "" as T
                }

               }else {
                   val error = Gson().fromJson(result.errorBody()!!.string(),ErrorResponse::class.java)
                   throw CustomExeption(error.message,error.status)
               }

           }catch (throwable:Throwable) {
               return when(throwable) {
                   is CustomExeption->{
                       throw CustomExeption( throwable.errorMessage ,throwable.status)
                   }
                   is SocketTimeoutException ->{
                       throw CustomExeption( TIMEOUT,-1)
                   }
                   is IOException ->{
                       Log.e("myExeptionsasfasf",throwable.localizedMessage.toString())
                     throw  CustomExeption( IOE,-2)
                   }else-> {
                       if (BuildConfig.DEBUG) {
                           Log.e("MyGeneralExeption",throwable.message?:"NoMessage")
                       }
                       throw CustomExeption(GENERAL,-3)
                   }
               }

           }
    }

    companion object ERRORS {
        val TIMEOUT = "Timeout"
        val IOE = "IOException"
        val GENERAL = "GeneralExeption"
    }

}