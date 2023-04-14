package com.example.domain.usecases.usermanageUseCase

import com.example.domain.models.RegisterNeed
import com.example.domain.models.UserManage
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class RegisterNeedsUseCase @Inject constructor(val userManageRepository: UserManageRepository) {
    operator fun invoke(
    ): Flow<Resource<RegisterNeed> > {
        return userManageRepository.registerNeeds()
    }

}