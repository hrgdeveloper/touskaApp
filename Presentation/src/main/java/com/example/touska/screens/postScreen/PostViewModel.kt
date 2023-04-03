package com.example.touska.screens.postScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.domain.usecases.loginUsecases.blocUseCases.BlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.CreateBlocUsecase
import com.example.domain.usecases.loginUsecases.blocUseCases.DeleteBlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.UpdateBlocUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.CreateFloorUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.DeleteFloorUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.GetFloorsUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.UpdateFloorUseCase
import com.example.domain.usecases.loginUsecases.postUseCases.CreatePostUsecase
import com.example.domain.usecases.loginUsecases.postUseCases.DeletePostUseCase
import com.example.domain.usecases.loginUsecases.postUseCases.GetPostsUsecase
import com.example.domain.usecases.loginUsecases.postUseCases.UpdatePostUseCase
import com.example.domain.usecases.loginUsecases.unitUseCases.CreateUnitUsecase
import com.example.domain.usecases.loginUsecases.unitUseCases.DeleteUnitUseCase
import com.example.domain.usecases.loginUsecases.unitUseCases.GetUnitsUsecase
import com.example.domain.usecases.loginUsecases.unitUseCases.UpdateUnitUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    val getPostsUsecase: GetPostsUsecase,
    val createPostUsecase: CreatePostUsecase,
    val updatePostUseCase: UpdatePostUseCase,
    val deletePostUseCase: DeletePostUseCase

) : ViewModel() {
    private val posts_ = MutableLiveData<Resource<MutableList<Post>>>()
    val posts: LiveData<Resource<MutableList<Post>>> get() = posts_

    private val addPost_ = MutableLiveData<Resource<Post>>()
    val addPost: LiveData<Resource<Post>> get() = addPost_

    private val updatePost_ = MutableLiveData<Resource<Any>>()
    val updatePost: LiveData<Resource<Any>> get() = updatePost_


    private val deletePost_ = MutableLiveData<Resource<Any>>()
    val deletePost: LiveData<Resource<Any>> get() = deletePost_


    fun getPosts() {
        viewModelScope.launch {
            getPostsUsecase().collect {
                posts_.postValue(it)
            }
        }
    }

    fun createPost(title: String) {
        if (title.isEmpty()) {
            addPost_.postValue(Resource.Failure("", 0, R.string.insert_post_name))
            return
        }
        viewModelScope.launch {
            createPostUsecase(title).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val newUnitList = mutableListOf<Post>().apply {
                            addAll(posts_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
                            add(resource.result)
                        }
                        posts_.postValue(Resource.Success(newUnitList))
                        addPost_.postValue(resource)
                    }
                    else -> {
                        addPost_.postValue(resource)
                    }
                }


            }

        }
    }

    fun updatePost(title: String, id: Int) {
        if (title.isEmpty()) {
            updatePost_.postValue(Resource.Failure("", 0, R.string.insert_post_name))
            return
        }
        viewModelScope.launch {
            updatePostUseCase(title, id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        posts_.postValue(Resource.Success(resource.result) )
                        updatePost_.postValue(Resource.Success(true))
                    }
                    else -> {
                        updatePost_.postValue(resource)
                    }
                }


            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            deletePostUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        posts_.postValue(Resource.Success(resource.result))
                        deletePost_.postValue(Resource.Success(true))
                    }
                    else -> {
                        deletePost_.postValue(resource)
                    }
                }

            }
        }
    }




}