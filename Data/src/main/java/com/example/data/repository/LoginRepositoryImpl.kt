package com.example.data.repository

import com.example.data.local.room.daos.UserDao
import com.example.data.local.sharedpref.PrefManager
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.User
import com.example.domain.repositories.LoginRepository
import com.example.shared.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor ( val prefManager: PrefManager ,
                                                val apiInterface: ApiInterface ,
                                                val userDao: UserDao ) : LoginRepository,SafeApiCall() {
    override suspend fun login(username: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.login(username,password) }
            prefManager.setValue(PrefManager.IS_LOGIN,true)
            prefManager.setValue(PrefManager.TOKEN,result.token)
            userDao.insertUser(result.toEntity())
            val user = userDao.getUser()
            emit(Resource.Success(user.toDomain()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }.flowOn(Dispatchers.IO)
}