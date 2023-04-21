package com.example.data.repository

import com.example.data.local.room.daos.FloorDao
import com.example.data.local.room.daos.PostDao
import com.example.data.local.room.daos.UnitDao
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.network.ApiInterface
import com.example.data.network.utils.CustomExeption
import com.example.data.network.utils.SafeApiCall
import com.example.domain.models.Floor
import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.domain.repositories.FloorRepository
import com.example.domain.repositories.PostRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    val apiInterface: ApiInterface,
    val postDao: PostDao,
) : PostRepository, SafeApiCall() {


    override fun getPosts() :Flow<Resource<MutableList<Post>>> = flow {
        emit(Resource.IsLoading)
        val posts_in_db = postDao.getPosts().map { it.toDomain() }
        try {
            val result = safeCall { apiInterface.getPosts() }
            postDao.deleteAndInsert(result.map { it.toEntity() })
            val posts = postDao.getPosts().map { it.toDomain() }
            emit(Resource.Success(posts.toMutableList()))

        }catch (e:CustomExeption) {
            if (posts_in_db.isNullOrEmpty()) {
                emit(Resource.Failure(e.errorMessage,e.status))
            }else {
                emit(Resource.Success(posts_in_db.toMutableList()))
            }
        }

    }

    override fun addPost( title: String): Flow<Resource<Post>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.createPost(title) }
            postDao.insertSinglePost(result.toEntity())
            emit(Resource.Success(postDao.getSinglePost(result.id).toDomain()))

        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun updatePost(
        title: String,
        id: Int,
    ): Flow<Resource<MutableList<Post>>> = flow {
        emit(Resource.IsLoading)
        try {
            val result = safeCall { apiInterface.updatePost(title,id) }
            postDao.updatePost(result.toEntity())
            val newlist = postDao.getPosts().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

    override fun deletePost(id: Int): Flow<Resource<MutableList<Post>>> = flow {
        emit(Resource.IsLoading)
        try {
            safeCall { apiInterface.deletePost(id) }
            postDao.deletePost(id)
            val newlist = postDao.getPosts().map { it.toDomain() }
            emit(Resource.Success(newlist.toMutableList()))
        }catch (e:CustomExeption) {
            emit(Resource.Failure(e.errorMessage,e.status))
        }
    }

}
