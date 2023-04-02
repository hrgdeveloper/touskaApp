package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.FloorEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface FloorDao {

    @Query("delete from Floor")
    suspend fun  deleteFloors()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertFloors(blocs: List<FloorEntity>)


    @Transaction
    suspend fun deleteAndInsert(floors: List<FloorEntity>){
        deleteFloors()
        insertFloors(floors)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleFloor(floorEntity: FloorEntity)

    @Query("select * from Floor")
    suspend fun getFloors():List<FloorEntity>

    @Query("select * from Floor where id = :id")
    suspend  fun getSingleFloor(id: Int):FloorEntity

    @Update
    suspend fun updateFloor(floor: FloorEntity)

    @Query("delete from floor where id = :floor_id")
    suspend fun deleteFloor(floor_id:Int)

//    @Query("update bloc set floors = :floors where id = :bloc_id")
//    suspend fun updateFloors(floors : List<FloorDto>, bloc_id:Int)







}