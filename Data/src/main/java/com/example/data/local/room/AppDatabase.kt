package com.example.data.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.room.daos.BlocDao
import com.example.data.local.room.daos.UserDao
import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.ProjectEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.local.room.utils.DataConverter

@Database(entities = [ProjectEntity::class,UserEntity::class,BlocEntity::class],
    version = 1,
    exportSchema = false,

)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    abstract fun blocDao() : BlocDao

}