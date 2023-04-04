package com.example.data.repository

import com.example.data.local.room.daos.ContractDao
import com.example.data.local.room.daos.FloorDao
import com.example.data.local.room.daos.PostDao
import com.example.data.local.room.daos.UnitDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Contract
import com.example.domain.models.Floor
import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.domain.repositories.ContractRepository
import com.example.domain.repositories.FloorRepository
import com.example.domain.repositories.PostRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContractRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val contractDao: ContractDao,
) : ContractRepository, SafeApiCall() {


    override fun getContracts(): Flow<Resource<MutableList<Contract>>> = flow {
        emit(Resource.IsLoading)
        val sotredInDb = contractDao.getContract().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getContracts() }
            contractDao.deleteAndInsert(result.map { it.toEntity() })
            val posts = contractDao.getContract().map { it.toDomain() }
            emit(Resource.Success(posts.toMutableList()))

        }catch (e:CustomExeption) {
            if (sotredInDb.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(sotredInDb.toMutableList()))
            }
        }


    }

    override fun addContract(title: String): Flow<Resource<Contract>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createContract(title) }
            contractDao.insertSingleContract(result.toEntity())
            emit(Resource.Success(contractDao.getSingleContract(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updateContract(title: String, id: Int): Flow<Resource<MutableList<Contract>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updateContract(title,id) }
            contractDao.updateContract(result.toEntity())
            val newlist = contractDao.getContract().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deleteContract(id: Int): Flow<Resource<MutableList<Contract>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deleteContract(id) }
            contractDao.deleteContract(id)
            val newlist = contractDao.getContract().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
