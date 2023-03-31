package com.example.touska.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.User
import com.example.domain.usecases.loginUsecases.LoginUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase)  : ViewModel(){

    private  val login_ = MutableLiveData<Resource<User>>()
    val login  : LiveData<Resource<User>> get() = login_


    fun login(email:String,password:String) {
        if (!email.isValidEmail()) {
            login_.postValue(Resource.Failure("",400,R.string.insert_email))
            return
        }

        if (password.isNullOrEmpty()) {
            login_.postValue(Resource.Failure("",400,R.string.insert_password))
            return
        }

        viewModelScope.launch {
            loginUseCase(email,password).collect {
                     login_.postValue(it)
            }
        }

    }


}