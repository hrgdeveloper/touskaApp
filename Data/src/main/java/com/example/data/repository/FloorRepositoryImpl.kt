package com.example.data.repository

import com.example.data.local.room.daos.BlocDao
import com.example.data.local.room.daos.FloorDao
import com.example.data.local.room.daos.UserDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntitiy
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.repositories.BlocRepository
import com.example.domain.repositories.FloorRepository
import com.example.shared.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FloorRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val floorDao: FloorDao,
) : FloorRepository, SafeApiCall() {


    override fun getFloors(bloc_id: Int): Flow<Resource<MutableList<Floor>>> = flow {

        emit(Resource.IsLoading)
        val floors_in_db = floorDao.getFloors().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getFloors(bloc_id) }
            floorDao.deleteAndInsert(result.map { it.toEntity() })
            val floors = floorDao.getFloors().map { it.toDomain() }
            emit(Resource.Success(floors.toMutableList()))

        }catch (e:CustomExeption) {
            if (floors_in_db.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(floors_in_db.toMutableList()))
            }
        }


    }

    override fun addFloor(bloc_id: Int, name: String, number: Int): Flow<Resource<Floor>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createFloor(name,number,bloc_id) }
            floorDao.insertSingleFloor(result.toEntity())
            emit(Resource.Success(floorDao.getSingleFloor(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateFloor(
        name: String,
        number: Int,
        id: Int,
        bloc_id: Int
    ): Flow<Resource<MutableList<Floor>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateFloor(name,number,id) }
            floorDao.updateFloor(result.toEntity())
            val newlist = floorDao.getFloors().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteBloc(id: Int,bloc_id: Int): Flow<Resource<MutableList<Floor>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteFloor(id) }
            floorDao.deleteFloor(id)
            val newlist = floorDao.getFloors().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
