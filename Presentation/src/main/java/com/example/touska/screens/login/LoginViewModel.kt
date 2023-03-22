package com.example.touska.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
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
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase,@ApplicationContext val context: Context)  : ViewModel(){

    private  val login_ = MutableLiveData<Resource<User>>()
    val login  : LiveData<Resource<User>> get() = login_


    fun login(email:String,password:String) {
        if (!email.isValidEmail()) {
            login_.postValue(Resource.Failure(context.getString(R.string.insert_email),400))
            return
        }

        if (password.isNullOrEmpty()) {
            login_.postValue(Resource.Failure(context.getString(R.string.insert_password),400))
            return
        }

        viewModelScope.launch {
            loginUseCase(email,password).collect {
                     login_.postValue(it)
            }
        }

    }


}