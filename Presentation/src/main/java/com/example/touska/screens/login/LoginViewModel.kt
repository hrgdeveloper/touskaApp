package com.example.touska.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.loginUsecases.LoginUseCase
import com.example.shared.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase)  : ViewModel(){

    private  val login_ = MutableLiveData<Resource<User>>()
    val login  : LiveData<Resource<User>> get() = login_


    fun login(username:String,password:String) {
        viewModelScope.launch {
            loginUseCase(username,password).collect {
                     login_.postValue(it)
            }
        }

    }


}