package com.example.domain.usecases.loginUsecases.homeUsecases

import com.example.domain.models.User
import com.example.domain.repositories.HomeRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(val homeRepository: HomeRepository) {

    suspend operator fun invoke() : User {
       return homeRepository.getUser()
    }

}