package com.example.data.local.room.daos

import androidx.room.*
import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.FloorDto
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.models.UserManage
import kotlinx.coroutines.flow.Flow

@Dao
interface UserManageDao {

    @Query("delete from UserManage")
    suspend fun  deleteUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertUsers(contracts: List<UserManageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertUser(user: UserManageEntity)

    @Update
    suspend  fun updateUser(user: UserManageEntity)



    @Transaction
    suspend fun deleteAndInsert(users: List<UserManageEntity>){
        deleteUsers()
        insertUsers(users)
    }

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend  fun insertSingleContract(contractEntity: ContractEntity)

   @Query("select * from usermanage where role_id = :role_id AND (:postId =0  OR post_id = :postId) And (:searchQuery = '' OR email LIKE '%' || :searchQuery || '%' OR mobile LIKE '%' || :searchQuery || '%' OR name LIKE '%' || :searchQuery || '%')")
   suspend fun getSpeceficUsers(role_id:Int ,postId :Int,searchQuery:String):List<UserManageEntity>



    @Query("select * from usermanage")
    suspend fun getAllUsers():List<UserManageEntity>

    @Query("select * from usermanage where qr_code like :qrCode")
    suspend fun getUser(qrCode:String): UserManageEntity?




//
//
//    @Query("select * from Contract where id = :id")
//    suspend  fun getSingleContract(id: Int):ContractEntity
//
//    @Update
//    suspend fun updateContract(contractEntity: ContractEntity)
//
//    @Query("delete from Contract where id = :id")
//    suspend fun deleteContract(id:Int)









}