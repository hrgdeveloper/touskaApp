package com.example.domain.usecases.usermanageUseCase

import com.example.domain.models.UserManage
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class RegisterUsersUseCase @Inject constructor(val userManageRepository: UserManageRepository) {

    operator fun invoke(
        name: String,
        email: String,
        password: String,
        mobile: String,
        role_id: Int,
        contract_type_id: Int?,
        project_id: Int,
        post_id: Int?,
        profile: File?
    ): Flow<Resource<String> > {
        return userManageRepository.register(
            name, email, password, mobile, role_id, contract_type_id,
            project_id, post_id, profile
        )
    }

}