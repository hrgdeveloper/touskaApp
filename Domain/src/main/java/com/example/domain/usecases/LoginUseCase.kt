package com.example.domain.usecases

import com.example.domain.models.Login
import com.example.domain.repositories.LoginRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor( val loginRepository: LoginRepository) {

   suspend  fun login(username:String, password:String) : Flow<Resource<Login>> {
        return loginRepository.login(username,password)
    }

}