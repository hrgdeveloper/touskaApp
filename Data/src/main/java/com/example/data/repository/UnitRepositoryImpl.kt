package com.example.data.repository

import com.example.data.local.room.daos.FloorDao
import com.example.data.local.room.daos.UnitDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Floor
import com.example.domain.models.Unitt
import com.example.domain.repositories.FloorRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnitRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val unitDao: UnitDao,
) : UnitRepository, SafeApiCall() {


    override fun getUnits(floor_id: Int): Flow<Resource<MutableList<Unitt>>> = flow {
        emit(Resource.IsLoading)
        val units_in_db = unitDao.getUnits().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getUnits(floor_id) }
            unitDao.deleteAndInsert(result.map { it.toEntity() })
            val units = unitDao.getUnits().map { it.toDomain() }
            emit(Resource.Success(units.toMutableList()))

        }catch (e:CustomExeption) {
            if (units_in_db.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(units_in_db.toMutableList()))
            }
        }


    }

    override fun addUnit(floor_id: Int, name: String): Flow<Resource<Unitt>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createUnit(name,floor_id) }
            unitDao.insertSingleUnit(result.toEntity())
            emit(Resource.Success(unitDao.getSingleUnit(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateUnit(
        name: String,
        id: Int,
    ): Flow<Resource<MutableList<Unitt>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateUnit(name,id) }
            unitDao.updateUnit(result.toEntity())
            val newlist = unitDao.getUnits().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteUnit(id: Int): Flow<Resource<MutableList<Unitt>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteUnit(id) }
            unitDao.deleteUnit(id)
            val newlist = unitDao.getUnits().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
