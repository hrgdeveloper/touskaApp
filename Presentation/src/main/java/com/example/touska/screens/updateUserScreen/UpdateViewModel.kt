package com.example.touska.screens.updateUserScreen

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.FileUtils
import com.example.domain.models.RegisterNeed
import com.example.domain.usecases.usermanageUseCase.RegisterNeedsUseCase

import com.example.domain.usecases.usermanageUseCase.RegisterUsersUseCase
import com.example.domain.usecases.usermanageUseCase.UpdateUserUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.utils.isValidEmail

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
   val registerNeedsUseCase: RegisterNeedsUseCase,
   val updateUserUseCase: UpdateUserUseCase

) : ViewModel() {




    private val needs_ = MutableLiveData<Resource<RegisterNeed>>()
    val needs: LiveData<Resource<RegisterNeed>> get() = needs_


    private val update_ = MutableLiveData<Resource<String>>()
    val update: LiveData<Resource<String>> get() = update_


    fun getRegisterNeeds() {
        viewModelScope.launch {
            registerNeedsUseCase().collect {
                needs_.postValue(it)
            }
        }
    }


    fun updateUser(name:String,email:String,mobile:String,uri: Uri?,contract_type_id : Int?,
                 post_id:Int?,context:Context,id:Int,description:String?
                 ){

        var profile : File?=null
        uri?.let {
            profile= FileUtils.getFile(context,uri)
        }
        if (name.isEmpty()) {
            update_.postValue(Resource.Failure("",400, R.string.insert_name))
            return
        }


        if (!email.isValidEmail()) {
            update_.postValue(Resource.Failure("",400, R.string.invalid_email))
            return
        }

        if (!mobile.startsWith("09") || mobile.length!=11) {
            update_.postValue(Resource.Failure("",400, R.string.bad_mobile))
            return
        }

        if (contract_type_id!=null && contract_type_id==0) {
            update_.postValue(Resource.Failure("",400, R.string.define_contract_type))
            return
        }

        if (post_id!=null && post_id==0) {
            update_.postValue(Resource.Failure("",400, R.string.define_post_type))
            return
        }


        viewModelScope.launch {
            updateUserUseCase(name,email,mobile,contract_type_id,post_id,profile,id,description).collect {
                update_.postValue(it)
            }
        }

    }



}