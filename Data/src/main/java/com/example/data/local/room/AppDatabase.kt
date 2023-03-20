package com.example.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.room.daos.UserDao
import com.example.data.local.room.enteties.Project
import com.example.data.local.room.enteties.UserEntity

@Database(entities = [Project::class,UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
}