package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.room.AppDatabase

import com.example.data.local.room.daos.UserDao
import com.example.shared.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context) : AppDatabase{
     return  Room.databaseBuilder(context,
       AppDatabase::class.java,Constants.DATABASE_NAME)
           .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase) : UserDao {
        return appDatabase.userDao()
    }
}