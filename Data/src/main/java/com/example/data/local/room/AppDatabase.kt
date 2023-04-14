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
        FloorEntity::class, UnitEntity::class, PostEntity::class,ActivityEntity::class,
       ContractEntity::class,WorkingTimeEntity::class,UserManageEntity::class
    ],
    version =4,
    exportSchema = true,
    autoMigrations = [AutoMigration(1,2),AutoMigration(2,3),AutoMigration(3,4)]
    )
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun blocDao(): BlocDao

    abstract fun floorDao(): FloorDao

    abstract fun unitDao(): UnitDao

    abstract fun postDao(): PostDao

    abstract fun activityDao(): ActivityDao

    abstract fun contractDao(): ContractDao

    abstract fun workingTimeDao(): WorkingTimeDao

    abstract fun userManageDao(): UserManageDao


}