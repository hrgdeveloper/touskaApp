package com.example.data.repository

import com.example.data.local.room.daos.UserDao
import com.example.data.local.sharedpref.PrefManager
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.User
import com.example.domain.repositories.HomeRepository
import com.example.domain.repositories.LoginRepository
import com.example.shared.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val userDao: UserDao
) : HomeRepository {
    override suspend fun getUser(): User {
        val userEntity = userDao.getUser()
        return userEntity.toDomain()


    }

}