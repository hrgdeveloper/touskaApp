package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Activity
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("delete from activity")
    suspend fun  deleteActivities()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertActivities(units: List<ActivityEntity>)


    @Transaction
    suspend fun deleteAndInsert(activities: List<ActivityEntity>){
        deleteActivities()
        insertActivities(activities)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleActivity(activityEntity: ActivityEntity)

    @Query("select * from activity")
    suspend fun getActivities():List<ActivityEntity>

    @Query("select * from activity where id = :id")
    suspend  fun getSingleActivity(id: Int):ActivityEntity

    @Update
    suspend fun updateActivity(activityEntity: ActivityEntity)

    @Query("delete from activity where id = :id")
    suspend fun deleteActivity(id:Int)









}