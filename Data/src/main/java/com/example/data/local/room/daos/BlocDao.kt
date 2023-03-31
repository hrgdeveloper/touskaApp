package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.domain.models.Bloc
import kotlinx.coroutines.flow.Flow

@Dao
interface BlocDao {

    @Query("delete from bloc")
    suspend fun  deleteBlocs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertBlocs(blocs: List<BlocEntity>)


    @Transaction
    suspend fun deleteAndInsert(blocs: List<BlocEntity>){
        deleteBlocs()
        insertBlocs(blocs)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertSingleBloc(blocs: BlocEntity)

    @Query("select * from Bloc")
    suspend fun getBlocs():List<BlocEntity>

    @Query("select * from Bloc where id = :id")
    suspend  fun getSingleBloc(id: Int):BlocEntity

    @Update
    suspend fun updateBloc(blocs: BlocEntity)

    @Query("delete from Bloc where id = :bloc_id")
    suspend fun deleteBloc(bloc_id:Int)


}