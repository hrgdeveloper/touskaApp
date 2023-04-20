package com.example.touska.screens.settingScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.sharedpref.PrefManager
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
class SettingViewModel @Inject constructor(
    val prefManager: PrefManager,
) : ViewModel() {

}