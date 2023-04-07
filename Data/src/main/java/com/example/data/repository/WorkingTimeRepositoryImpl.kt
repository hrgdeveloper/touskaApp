package com.example.data.repository

import com.example.data.local.room.daos.ActivityDao
import com.example.data.local.room.daos.WorkingTimeDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Activity
import com.example.domain.models.WorkingTime
import com.example.domain.repositories.ActivityRepository
import com.example.domain.repositories.WorkingTimeRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkingTimeRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val workingTimeDao: WorkingTimeDao,
) : WorkingTimeRepository, SafeApiCall() {


    override fun getWorkingTimes(): Flow<Resource<MutableList<WorkingTime>>>  = flow {

        emit(Resource.IsLoading)
        val cached = workingTimeDao.getWorkingTimes().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getWorkingTimes() }
            workingTimeDao.deleteAndInsert(result.map { it.toEntity() })
            val activities = workingTimeDao.getWorkingTimes().map { it.toDomain() }
            emit(Resource.Success(activities.toMutableList()))

        }catch (e:CustomExeption) {
            if (cached.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(cached.toMutableList()))
            }
        }


    }

    override fun addWorkingTime(
        title: String,
        startTime: String,
        endTime: String
    ): Flow<Resource<WorkingTime>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createWorkingTime(title,startTime,endTime) }
            workingTimeDao.insertSingleWorkingTime(result.toEntity())
            emit(Resource.Success(workingTimeDao.getSingleWorkingTime(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateWorkingTime(
        title: String,
        startTime: String,
        endTime: String,
        id: Int
    ): Flow<Resource<MutableList<WorkingTime>>>  = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateWorkingTime(title, startTime,endTime, id) }
            workingTimeDao.updateWorkingTime(result.toEntity())
            val newlist = workingTimeDao.getWorkingTimes().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteWorkingTime(id: Int): Flow<Resource<MutableList<WorkingTime>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteWorkingTime(id) }
            workingTimeDao.deleteWorkingTimes(id)
            val newlist = workingTimeDao.getWorkingTimes().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
