package com.example.domain.repositories

import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts() : Flow<Resource<MutableList<Post>>>
    fun addPost(title:String) :  Flow<Resource<Post>>
    fun updatePost(title : String,id:Int) : Flow<Resource<MutableList<Post>>>
    fun deletePost(id:Int) : Flow<Resource<MutableList<Post>>>

}