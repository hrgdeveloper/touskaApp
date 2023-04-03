package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.room.AppDatabase
import com.example.data.local.room.daos.*

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

    @Provides
    @Singleton
    fun provideBlocDao(appDatabase: AppDatabase) : BlocDao {
        return appDatabase.blocDao()
    }

    @Provides
    @Singleton
    fun provideFloorDao(appDatabase: AppDatabase) : FloorDao {
        return appDatabase.floorDao()
    }

    @Provides
    @Singleton
    fun provideUnitDao(appDatabase: AppDatabase) : UnitDao {
        return appDatabase.unitDao()
    }


    @Provides
    @Singleton
    fun providePostDao(appDatabase: AppDatabase) : PostDao {
        return appDatabase.postDao()
    }



}