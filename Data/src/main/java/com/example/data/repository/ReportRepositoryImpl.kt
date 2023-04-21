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
    override fun reportNeeds(worker_id:Int): Flow<Resource<ReportNeed>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.reportNeeds(worker_id) }.toDomain()
            emit(Resource.Success(result))
        }catch (customExeption : CustomExeption) {
            emit(Resource.Failure(customExeption.errorMessage,customExeption.status))
        }
    }

}
