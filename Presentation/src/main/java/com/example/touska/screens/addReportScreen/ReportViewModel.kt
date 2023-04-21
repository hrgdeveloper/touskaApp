package com.example.touska.screens.addReportScreen

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.FileUtils
import com.example.domain.models.RegisterNeed
import com.example.domain.models.ReportNeed
import com.example.domain.usecases.reportUseCase.ReportNeedsUseCase

import com.example.domain.usecases.usermanageUseCase.RegisterNeedsUseCase
import com.example.domain.usecases.usermanageUseCase.RegisterUsersUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.utils.isValidEmail

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
   val reportNeedsUseCase: ReportNeedsUseCase,
   val registerUsersUseCase: RegisterUsersUseCase

) : ViewModel() {
    private val needs_ = MutableLiveData<Resource<ReportNeed>>()
    val needs: LiveData<Resource<ReportNeed>> get() = needs_


    private val register_ = MutableLiveData<Resource<String>>()
    val register: LiveData<Resource<String>> get() = register_


    fun getReportNeeds(worker_id:Int) {
        viewModelScope.launch {
            reportNeedsUseCase(worker_id).collect {
                needs_.postValue(it)
            }
        }
    }




//    fun register(name:String,password:String,email:String,mobile:String,role_id:Int,uri: Uri?,contract_type_id : Int?,
//                 post_id:Int?,project_id:Int,context:Context
//                 ){
//
//        var profile : File?=null
//        uri?.let {
//            profile= FileUtils.getFile(context,uri)
//        }
//        if (name.isEmpty()) {
//            register_.postValue(Resource.Failure("",400, R.string.insert_name))
//            return
//        }
//        if (password.length<6) {
//            register_.postValue(Resource.Failure("",400, R.string.weak_password))
//            return
//        }
//
//        if (!email.isValidEmail()) {
//            register_.postValue(Resource.Failure("",400, R.string.invalid_email))
//            return
//        }
//
//        if (!mobile.startsWith("09") || mobile.length!=11) {
//            register_.postValue(Resource.Failure("",400, R.string.bad_mobile))
//            return
//        }
//
//        if (contract_type_id!=null && contract_type_id==0) {
//            register_.postValue(Resource.Failure("",400, R.string.define_contract_type))
//            return
//        }
//
//        if (post_id!=null && post_id==0) {
//            register_.postValue(Resource.Failure("",400, R.string.define_post_type))
//            return
//        }
//
//
//        viewModelScope.launch {
//            registerUsersUseCase(name,email,password,mobile,role_id,contract_type_id,project_id,post_id,profile).collect {
//                register_.postValue(it)
//            }
//        }
//
//    }



}