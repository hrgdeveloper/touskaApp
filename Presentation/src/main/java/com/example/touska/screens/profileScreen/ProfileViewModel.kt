package com.example.touska.screens.profileScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.UserManage
import com.example.domain.usecases.loginUsecases.activityUseCases.DeleteActivityUseCase
import com.example.domain.usecases.loginUsecases.activityUseCases.UpdateActivityUseCase
import com.example.domain.usecases.loginUsecases.homeUsecases.GetUserUseCase
import com.example.domain.usecases.usermanageUseCase.GetSpeceficUsersUseCase
import com.example.domain.usecases.usermanageUseCase.GetuserUsecase
import com.example.shared.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getuserUsecase: GetuserUsecase,



) : ViewModel() {
    private val user_ = MutableLiveData<Resource<UserManage>>()
    val user: LiveData<Resource<UserManage>> get() = user_




    fun getUser(qrCode:String) {
        viewModelScope.launch {
            getuserUsecase(qrCode).collect {
                user_.postValue(it)
            }
        }
    }








}