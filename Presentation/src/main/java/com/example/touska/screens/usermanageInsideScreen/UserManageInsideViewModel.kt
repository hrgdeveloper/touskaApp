package com.example.touska.screens.usermanageInsideScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserManage
import com.example.domain.usecases.loginUsecases.activityUseCases.DeleteActivityUseCase
import com.example.domain.usecases.loginUsecases.activityUseCases.UpdateActivityUseCase
import com.example.domain.usecases.loginUsecases.homeUsecases.GetUserUseCase
import com.example.domain.usecases.usermanageUseCase.GetSpeceficUsersUseCase
import com.example.shared.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserManageInsideViewModel @Inject constructor(
    val getSpeceficUserUseCase: GetSpeceficUsersUseCase,

    val updateActivityUseCase: UpdateActivityUseCase,
    val deleteActivityUseCase: DeleteActivityUseCase

) : ViewModel() {
    private val users_ = MutableLiveData<MutableList<UserManage>>()
    val users: LiveData<MutableList<UserManage>> get() = users_

    private val speceficUsers_ = MutableLiveData<MutableList<UserManage>>()
    val speceficUsers: LiveData<MutableList<UserManage>> get() = speceficUsers_

    private val updateActivity_ = MutableLiveData<Resource<Any>>()
    val updateActivity: LiveData<Resource<Any>> get() = updateActivity_


    private val deleteActivity_ = MutableLiveData<Resource<Any>>()
    val deleteActivity : LiveData<Resource<Any>> get() = deleteActivity_


    fun getSpeceficUsers(role_id: Int,searchQuery:String,post_id:Int) {
        viewModelScope.launch {
            getSpeceficUserUseCase(role_id,searchQuery,post_id).collect {
                users_.postValue(it)
            }
        }
    }




//    fun createActivity(title: String, postId: Int) {
//        if (title.isEmpty()) {
//            addActivity_.postValue(Resource.Failure("", 0, R.string.insert_activity_name))
//            return
//        }
//        viewModelScope.launch {
//            createAtivityUsecase(title,postId).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        val newFloorList = mutableListOf<Activity>().apply {
//                            addAll(activities_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
//                            add(resource.result)
//                        }
//                        activities_.postValue(Resource.Success(newFloorList))
//                        addActivity_.postValue(resource)
//                    }
//                    else -> {
//                        addActivity_.postValue(resource)
//                    }
//                }
//
//
//            }
//
//        }
//    }
//
//    fun updateActivity(title: String, id: Int) {
//        if (title.isEmpty()) {
//            updateActivity_.postValue(Resource.Failure("", 0, R.string.insert_activity_name))
//            return
//        }
//        viewModelScope.launch {
//            updateActivityUseCase(title,id).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        activities_.postValue(Resource.Success(resource.result) )
//                        updateActivity_.postValue(Resource.Success(true))
//                    }
//                    else -> {
//                        updateActivity_.postValue(resource)
//                    }
//                }
//
//
//            }
//        }
//    }
//
//    fun deleteActivity(id: Int) {
//        viewModelScope.launch {
//            deleteActivityUseCase(id).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        activities_.postValue(Resource.Success(resource.result))
//                        deleteActivity_.postValue(Resource.Success(true))
//                    }
//                    else -> {
//                        deleteActivity_.postValue(resource)
//                    }
//                }
//
//            }
//        }
//    }




}