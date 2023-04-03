package com.example.data.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.room.daos.*
import com.example.data.local.room.enteties.*
import com.example.data.local.room.utils.DataConverter

@Database(
    entities = [ProjectEntity::class, UserEntity::class, BlocEntity::class,
        FloorEntity::class, UnitEntity::class, PostEntity::class,ActivityEntity::class
    ],
    version =1,
    exportSchema = true,
    )
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun blocDao(): BlocDao

    abstract fun floorDao(): FloorDao

    abstract fun unitDao(): UnitDao

    abstract fun postDao(): PostDao

    abstract fun activityDao(): ActivityDao

}