package com.example.touska

import android.app.Application
import com.example.data.local.sharedpref.PrefManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class MyApp : Application(){

    @Inject lateinit var prefManager: PrefManager
    var language = ""



    override fun onCreate() {
        super.onCreate()
        instance=this
        language=prefManager.getValue(PrefManager.Language, String::class,"fa")
    }


    companion object {
        lateinit var instance: MyApp
            private set
    }

}