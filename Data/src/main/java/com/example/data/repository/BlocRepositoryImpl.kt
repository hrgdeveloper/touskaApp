package com.example.data.repository

import com.example.data.local.room.daos.BlocDao
import com.example.data.local.room.daos.FloorDao
import com.example.data.local.room.daos.UserDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Bloc
import com.example.domain.repositories.BlocRepository
import com.example.shared.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BlocRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val blocDao: BlocDao,
    val floorDao : FloorDao
) : BlocRepository, SafeApiCall() {

    override fun getBlocs(): Flow<Resource<MutableList<Bloc>>> = flow {
        emit(Resource.IsLoading)
        val blocs_in_db = blocDao.getBlocs().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getBlocs() }
            blocDao.deleteAndInsert(result.map { it.toEntity() })
            val blocs = blocDao.getBlocs().map { it.toDomain() }
            emit(Resource.Success(blocs.toMutableList()))

        }catch (e:CustomExeption) {
            if (blocs_in_db.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(blocs_in_db.toMutableList()))
            }
        }

    }.flowOn(Dispatchers.IO)

    override fun addBloc(name:String): Flow<Resource<Bloc>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createBLoc(name) }
            blocDao.insertSingleBloc(result.toEntity())
            emit(Resource.Success(blocDao.getSingleBloc(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateBloc(name: String,id:Int): Flow<Resource<MutableList<Bloc>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateBloc(name,id) }
            blocDao.updateBloc(result.toEntity())
            val newlist = blocDao.getBlocs().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteBloc(id: Int): Flow<Resource<MutableList<Bloc>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteBloc(id) }
            blocDao.deleteBloc(id)
            val newlist = blocDao.getBlocs().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
