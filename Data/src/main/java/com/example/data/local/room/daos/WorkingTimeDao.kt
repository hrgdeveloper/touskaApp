package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.local.room.enteties.WorkingTimeEntity
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkingTimeDao {

    @Query("delete from WorkingTime")
    suspend fun  deleteWorkingTimes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertWorkingTimes(blocs: List<WorkingTimeEntity>)


    @Transaction
    suspend fun deleteAndInsert(workingTimes: List<WorkingTimeEntity>){
        deleteWorkingTimes()
        insertWorkingTimes(workingTimes)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleWorkingTime(workingTimeEntity: WorkingTimeEntity)

    @Query("select * from WorkingTime")
    suspend fun getWorkingTimes():List<WorkingTimeEntity>

    @Query("select * from WorkingTime where id = :id")
    suspend  fun getSingleWorkingTime(id: Int):WorkingTimeEntity

    @Update
    suspend fun updateWorkingTime(workingTimeEntity: WorkingTimeEntity)

    @Query("delete from WorkingTime where id = :id")
    suspend fun deleteWorkingTimes(id:Int)









}