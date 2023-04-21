package com.example.domain.usecases.usermanageUseCase

import com.example.domain.models.UserManage
import com.example.domain.repositories.UserManageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpeceficUsersUseCase @Inject constructor(val userManageRepository: UserManageRepository) {

    operator fun invoke(role_id:Int,searchQuery : String,post_id:Int ) : Flow<MutableList<UserManage>> {
        return userManageRepository.getSpeceficUsers(role_id,searchQuery,post_id)
    }

}