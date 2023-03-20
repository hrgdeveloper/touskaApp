package com.example.data.local.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.room.enteties.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun insertUser(userEntity: UserEntity)

    @Query("select * from User")
    fun getUser():UserEntity

}