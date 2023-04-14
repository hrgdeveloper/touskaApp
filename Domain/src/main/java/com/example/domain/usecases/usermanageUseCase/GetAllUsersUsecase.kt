package com.example.domain.usecases.usermanageUseCase

import com.example.domain.models.Contract
import com.example.domain.models.UserManage
import com.example.domain.repositories.ContractRepository
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUsecase @Inject constructor(val userManageRepository: UserManageRepository) {

    operator fun invoke() : Flow<Resource<MutableList<UserManage>>> {
        return userManageRepository.getAllusers()
    }

}