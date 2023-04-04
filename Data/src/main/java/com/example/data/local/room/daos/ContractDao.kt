package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import kotlinx.coroutines.flow.Flow

@Dao
interface ContractDao {

    @Query("delete from Contract")
    suspend fun  deleteContracts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertContracts(contracts: List<ContractEntity>)


    @Transaction
    suspend fun deleteAndInsert(contracts: List<ContractEntity>){
        deleteContracts()
        insertContracts(contracts)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleContract(contractEntity: ContractEntity)

    @Query("select * from Contract")
    suspend fun getContract():List<ContractEntity>

    @Query("select * from Contract where id = :id")
    suspend  fun getSingleContract(id: Int):ContractEntity

    @Update
    suspend fun updateContract(contractEntity: ContractEntity)

    @Query("delete from Contract where id = :id")
    suspend fun deleteContract(id:Int)









}