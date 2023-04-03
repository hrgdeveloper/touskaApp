package com.example.domain.usecases.loginUsecases.activityUseCases

import com.example.domain.models.Activity
import com.example.domain.models.Post
import com.example.domain.repositories.ActivityRepository
import com.example.domain.repositories.PostRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateActivityUseCase @Inject constructor(val activityRepository: ActivityRepository) {

    operator fun invoke(title:String, id:Int) : Flow<Resource<MutableList<Activity>>> {
        return activityRepository.updateActivity(title,id)
    }

}