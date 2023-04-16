package com.example.domain.usecases.usermanageUseCase

import com.example.domain.models.UserManage
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(val userManageRepository: UserManageRepository) {

    operator fun invoke(
        name: String,
        email: String,
        mobile: String,
        contract_type_id: Int?,
        post_id: Int?,
        profile: File?,
        id:Int
    ): Flow<Resource<String> > {
        return userManageRepository.updateUser(
            name,email,mobile,contract_type_id,post_id,profile,id
        )
    }

}