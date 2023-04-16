package com.example.data.repository

import com.example.data.local.room.daos.*
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.dtos.RegisterRequestDto
import com.example.data.network.dtos.UserManageDto
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.*
import com.example.domain.repositories.*
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class UserManageRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val userManageDao: UserManageDao,
) : UserManageRepository, SafeApiCall() {


    override fun getAllusers(): Flow<Resource<MutableList<UserManage>>> = flow {
        emit(Resource.IsLoading)
        val sotredInDb = userManageDao.getAllUsers().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getAllUsers() }
            userManageDao.deleteAndInsert(result.map { it.toEntity() })
            val users = userManageDao.getAllUsers().map { it.toDomain() }
            emit(Resource.Success(users.toMutableList()))

        }catch (e:CustomExeption) {
            if (sotredInDb.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(sotredInDb.toMutableList()))
            }
        }
    }

    override fun getSpeceficUsers(role_id: Int,searchQuery:String): Flow<MutableList<UserManage>> = flow {
        if (searchQuery.isEmpty()) {
            val users = userManageDao.getSpeceficUsers(role_id).map { it.toDomain() }
            emit(users.toMutableList())
        }else {
            val users = userManageDao.getSpeceficUsersWithSearch(role_id,searchQuery).map { it.toDomain() }
            emit(users.toMutableList())
        }


    }

    override fun registerNeeds(): Flow<Resource<RegisterNeed>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.registerNeeds() }.toDomain()
            emit(Resource.Success(result))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }


    override fun register(
        name: String,
        email: String,
        password: String,
        mobile: String,
        role_id: Int,
        contract_type_id: Int?,
        project_id: Int,
        post_id: Int?,
        profile: File?
    ): Flow<Resource<String> > = flow {
        emit(Resource.IsLoading)
        try {
            var pic_part : MultipartBody.Part? =null
            profile?.let {
                val requestFile= profile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                pic_part=MultipartBody.Part.createFormData("pic", profile.name, requestFile)
            }

            val params = mutableMapOf<String, RequestBody>()
            params["name"] = name.toRequestBody(MultipartBody.FORM)
            params["email"] = email.toRequestBody(MultipartBody.FORM)
            params["password"] = password.toRequestBody(MultipartBody.FORM)
            params["mobile"] = mobile.toRequestBody(MultipartBody.FORM)
            params["role_id"] = role_id.toString().toRequestBody(MultipartBody.FORM)
            params["project_id"] = project_id.toString().toRequestBody(MultipartBody.FORM)
            if (contract_type_id!=null) {
                params["contract_type_id"] = contract_type_id.toString().toRequestBody(MultipartBody.FORM)
            }

            if (post_id!=null) {
                params["post_id"] = post_id.toString().toRequestBody(MultipartBody.FORM)
            }


            val result = safeCall {
                apiInterface.register(
                    pic_part,params
                )
            }
            userManageDao.insertUser(result.toEntity())
            emit(Resource.Success("Sucesss"))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateUser(
        name: String,
        email: String,
        mobile: String,
        contract_type_id: Int?,
        post_id: Int?,
        profile: File?,
        id:Int
    ): Flow<Resource<String>> = flow {
        try {
            var pic_part : MultipartBody.Part? =null
            profile?.let {
                val requestFile= profile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                pic_part=MultipartBody.Part.createFormData("pic", profile.name, requestFile)
            }

            val params = mutableMapOf<String, RequestBody>()
            params["name"] = name.toRequestBody(MultipartBody.FORM)
            params["email"] = email.toRequestBody(MultipartBody.FORM)
            params["mobile"] = mobile.toRequestBody(MultipartBody.FORM)

            if (contract_type_id!=null) {
                params["contract_type_id"] = contract_type_id.toString().toRequestBody(MultipartBody.FORM)
            }

            if (post_id!=null) {
                params["post_id"] = post_id.toString().toRequestBody(MultipartBody.FORM)
            }


            val result = safeCall {
                apiInterface.updateUser(
                    pic_part,params,id
                )
            }
            userManageDao.updateUser(result.toEntity())
            emit(Resource.Success("Sucesss"))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }




}
