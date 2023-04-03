package com.example.data.repository

import com.example.data.local.room.daos.ActivityDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Activity
import com.example.domain.repositories.ActivityRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val activityDao: ActivityDao,
) : ActivityRepository, SafeApiCall() {


    override fun getActivities(post_id: Int): Flow<Resource<MutableList<Activity>>>  = flow {

        emit(Resource.IsLoading)
        val activities_in_db = activityDao.getActivities().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getActivities(post_id) }
            activityDao.deleteAndInsert(result.map { it.toEntity() })
            val activities = activityDao.getActivities().map { it.toDomain() }
            emit(Resource.Success(activities.toMutableList()))

        }catch (e:CustomExeption) {
            if (activities_in_db.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(activities_in_db.toMutableList()))
            }
        }


    }

    override fun addActivity(title: String, post_id: Int): Flow<Resource<Activity>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createActivity(title,post_id) }
            activityDao.insertSingleActivity(result.toEntity())
            emit(Resource.Success(activityDao.getSingleActivity(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateActivity(title: String, id: Int): Flow<Resource<MutableList<Activity>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateActivity(title,id) }
            activityDao.updateActivity(result.toEntity())
            val newlist = activityDao.getActivities().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteActivity(id: Int): Flow<Resource<MutableList<Activity>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteActivity(id) }
            activityDao.deleteActivity(id)
            val newlist = activityDao.getActivities().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
