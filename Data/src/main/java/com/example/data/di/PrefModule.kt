package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.shared.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object PrefModule {

    @Provides
    @Singleton
    fun providePref(@ApplicationContext context: Context) : SharedPreferences{
       return context.getSharedPreferences(Constants.PREF_NAME,0)
    }

    @Singleton
    @Provides
    fun getEditor(sharedPreferences: SharedPreferences) : Editor {
       return sharedPreferences.edit()
    }


}