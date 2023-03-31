package com.example.data.network.utils

import android.os.Build
import com.example.data.local.sharedpref.PrefManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(val prefManager: PrefManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        if (prefManager.getValue(PrefManager.IS_LOGIN,Boolean::class,false)) {
            request.header("Authorization", "Bearer "+
            prefManager.getValue(PrefManager.TOKEN, String::class,"")
            )
        }
      return  chain.proceed(request.build())

    }
}