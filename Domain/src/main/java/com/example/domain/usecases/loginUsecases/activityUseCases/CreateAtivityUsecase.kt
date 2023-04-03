package com.example.domain.usecases.loginUsecases.activityUseCases

import com.example.domain.models.Activity
import com.example.domain.models.Post
import com.example.domain.repositories.ActivityRepository
import com.example.domain.repositories.PostRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateAtivityUsecase @Inject constructor(val activityRepository: ActivityRepository) {

    operator fun invoke(title:String,post_id:Int) : Flow<Resource<Activity>> {
        return activityRepository.addActivity(title,post_id)
    }

}