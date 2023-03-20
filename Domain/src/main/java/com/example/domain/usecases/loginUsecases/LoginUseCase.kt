package com.example.domain.usecases.loginUsecases

import com.example.domain.models.User
import com.example.domain.repositories.LoginRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor( val loginRepository: LoginRepository) {

   suspend operator fun invoke(username:String, password:String) : Flow<Resource<User>> {
        return loginRepository.login(username,password)
    }

}