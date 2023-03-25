package com.example.touska.screens.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.loginUsecases.homeUsecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val getUserUseCase: GetUserUseCase) : ViewModel() {

    private val user_ = MutableLiveData<User>()
    val user : LiveData<User> get() = user_


    fun getUser(){
        viewModelScope.launch() {
            user_.postValue(getUserUseCase.invoke())
        }

    }

}