package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.FloorEntity
import com.example.data.local.room.enteties.UnitEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitDao {

    @Query("delete from Unitt")
    suspend fun  deleteUnitts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertUnitts(units: List<UnitEntity>)


    @Transaction
    suspend fun deleteAndInsert(units: List<UnitEntity>){
        deleteUnitts()
        insertUnitts(units)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleUnit(unitEntity: UnitEntity)

    @Query("select * from Unitt")
    suspend fun getUnits():List<UnitEntity>

    @Query("select * from Unitt where id = :id")
    suspend  fun getSingleUnit(id: Int):UnitEntity

    @Update
    suspend fun updateUnit(unitEntity: UnitEntity)

    @Query("delete from Unitt where id = :unit_id")
    suspend fun deleteUnit(unit_id:Int)









}