package com.example.domain.usecases.loginUsecases.postUseCases

import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.domain.repositories.PostRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(val postRepository: PostRepository) {

    operator fun invoke(title:String, id:Int) : Flow<Resource<MutableList<Post>>> {
        return postRepository.updatePost(title,id)
    }

}