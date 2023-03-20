package com.example.domain.repositories

import com.example.domain.models.User
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow


interface LoginRepository {
  suspend fun login(username:String,password:String) : Flow<Resource<User>>
}