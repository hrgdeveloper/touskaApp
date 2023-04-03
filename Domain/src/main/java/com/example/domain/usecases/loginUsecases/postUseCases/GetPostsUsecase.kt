package com.example.domain.usecases.loginUsecases.postUseCases

import com.example.domain.models.Post
import com.example.domain.repositories.PostRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUsecase @Inject constructor(val postRepository: PostRepository) {

    operator fun invoke() : Flow<Resource<MutableList<Post>>> {
        return postRepository.getPosts()
    }

}