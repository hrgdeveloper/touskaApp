package com.example.data.repository

import com.example.data.local.room.daos.BlocDao
import com.example.data.local.room.daos.UserDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntitiy
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
    val blocDao: BlocDao,
) : FloorRepository, SafeApiCall() {


    override fun getFloors(bloc_id: Int): Flow<MutableList<Floor>> = flow {

        val bloc = blocDao.getSingleBloc(bloc_id)
        val floors = bloc.floors.map { it.toDomain() }
        emit(floors.toMutableList())
    }

    override fun addFloor(bloc_id: Int, name: String, number: Int): Flow<Resource<Floor>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createFloor(name, number, bloc_id) }
            val floors = blocDao.getSingleBloc(bloc_id).floors.toMutableList()
            floors.add(result)
            blocDao.updateFloors(floors, bloc_id)
            emit(Resource.Success(result.toDomain()))

        } catch (e: CustomExeption) {
            emit(Resource.Failure(e.errorMessage, e.status))
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
            val result = safeCall { apiInterface.updateFloor(name, number, id) }
            val floors = blocDao.getSingleBloc(bloc_id).floors.toMutableList()
            floors.forEachIndexed { index, floorDto ->
                if (floorDto.id == result.id) {
                    floors.set(index, result)
                }
            }
            blocDao.updateFloors(floors, bloc_id)
            val finalBloc = blocDao.getSingleBloc(bloc_id)
            val finalFloors = finalBloc.floors.map { it.toDomain() }
            emit(Resource.Success(finalFloors.toMutableList()))

        } catch (e: CustomExeption) {
            emit(Resource.Failure(e.errorMessage, e.status))
        }
    }

    override fun deleteBloc(id: Int,bloc_id: Int): Flow<Resource<MutableList<Floor>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteFloor(id) }
            val floors = blocDao.getSingleBloc(bloc_id).floors.toMutableList()
            for (i in floors.indices) {
                if (floors[i].id == id) {
                    floors.removeAt(i)
                    break // Break out of the loop after removing the floor
                }
            }
            blocDao.updateFloors(floors, bloc_id)
            val finalBloc = blocDao.getSingleBloc(bloc_id)
            val finalFloors = finalBloc.floors.map { it.toDomain() }
            emit(Resource.Success(finalFloors.toMutableList()))

        } catch (e: CustomExeption) {
            emit(Resource.Failure(e.errorMessage, e.status))
        }
    }

}
