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

class ReportRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
) : ReportRepository, SafeApiCall() {
    override fun reportNeeds(worker_id: Int): Flow<Resource<ReportNeed>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.reportNeeds(worker_id) }.toDomain()
            emit(Resource.Success(result))
        } catch (customExeption: CustomExeption) {
            emit(Resource.Failure(customExeption.errorMessage, customExeption.status))
        }
    }

    override fun reportNeedsFull(): Flow<Resource<ReportNeedFull>>  = flow{
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.reportNeedsFull() }.toDomain()
            emit(Resource.Success(result))
        } catch (customExeption: CustomExeption) {
            emit(Resource.Failure(customExeption.errorMessage, customExeption.status))
        }
    }

    override fun addReport(
        workerId: Int,
        superVisorId: Int,
        activityId: Int,
        blockId: Int,
        floorId: Int?,
        unitId: Int?,
        description: String?,
        times: String,
        pic:File?
    ): Flow<Resource<String>> = flow {
        emit(Resource.IsLoading)

        var pic_part : MultipartBody.Part? =null
        pic?.let {
            val requestFile= pic.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            pic_part=MultipartBody.Part.createFormData("pic", pic.name, requestFile)
        }

        val params = mutableMapOf<String, RequestBody>()
        params["worker_id"] = workerId.toString().toRequestBody(MultipartBody.FORM)
        params["supervisor_id"] = superVisorId.toString().toRequestBody(MultipartBody.FORM)
        params["activity_id"] = activityId.toString().toRequestBody(MultipartBody.FORM)
        params["bloc_id"] = blockId.toString().toRequestBody(MultipartBody.FORM)
        params["times"] = times.toRequestBody(MultipartBody.FORM)
        floorId?.let {
            params["floor_id"] = it.toString().toRequestBody(MultipartBody.FORM)
        }
        unitId?.let {
            params["unit_id"]=it.toString().toRequestBody(MultipartBody.FORM)
        }
        description?.let {
            params["description"]=it.toRequestBody(MultipartBody.FORM)
        }


        try {
            safeCall {
                apiInterface.addReport(
                    pic_part,params
                )
            }
            emit(Resource.Success("Report has Added Successfully"))
        } catch (customExeption: CustomExeption) {
            emit(Resource.Failure(customExeption.errorMessage, customExeption.status))
        }
    }

    override fun getReports(
        blockId: Int?,
        floorId: Int?,
        unitId: Int?,
        superVisorId: Int?,
        workerId: Int?,
        postId: Int?,
        activityId: Int?,
        contractTypeId: Int?,
        startDate: String?,
        endDate: String?,
        contractorId:Int?
    ): Flow<Resource<MutableList<Report>>> = flow {
        emit(Resource.IsLoading)
        try {
            emit(Resource.IsLoading)
            val result =  safeCall { apiInterface.getReports(
                blockId,
                floorId,
                unitId,
                superVisorId,
                workerId,
                postId,
                activityId,
                contractTypeId,
                startDate,
                endDate,
                contractorId
            ) }
            emit(Resource.Success(result.map { it.toDomain() }.toMutableList()))
        } catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
