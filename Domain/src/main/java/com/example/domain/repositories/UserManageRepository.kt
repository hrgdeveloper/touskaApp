package com.example.domain.repositories

import com.example.domain.models.*
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserManageRepository {
    fun getAllusers() : Flow<Resource<MutableList<UserManage>>>
    fun getSpeceficUsers(role_id:Int,searchQuery:String,post_id: Int) : Flow<MutableList<UserManage>>
    fun register(
        name:String,
        email:String,
        password:String,
        mobile:String,
        role_id:Int,
        contract_type_id:Int?,
        project_id:Int,
        post_id:Int?,
        profile:File?,
        contractorId:Int?
    ) : Flow<Resource<String>>

    fun updateUser(
        name:String,
        email:String,
        mobile:String,
        contract_type_id:Int?,
        post_id:Int?,
        profile:File?,
        id:Int
    ) : Flow<Resource<String>>


    fun registerNeeds(): Flow<Resource<RegisterNeed>>

    fun getUser(qrCode:String) : Flow<Resource<UserManage>>



}