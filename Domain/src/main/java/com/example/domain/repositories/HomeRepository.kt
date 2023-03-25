package com.example.domain.repositories

import com.example.domain.models.User
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
  suspend fun getUser() : User
}