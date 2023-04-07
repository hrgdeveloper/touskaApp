package com.example.touska.screens.workingTimeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Activity
import com.example.domain.models.WorkingTime
import com.example.domain.usecases.loginUsecases.activityUseCases.CreateAtivityUsecase
import com.example.domain.usecases.loginUsecases.activityUseCases.DeleteActivityUseCase
import com.example.domain.usecases.loginUsecases.activityUseCases.GetActivityUsecase
import com.example.domain.usecases.loginUsecases.activityUseCases.UpdateActivityUseCase
import com.example.domain.usecases.workingTimeUseCases.CreateWorkingTimeUseCase
import com.example.domain.usecases.workingTimeUseCases.DeleteWorkingTimeUseCase
import com.example.domain.usecases.workingTimeUseCases.GetWorkingTimesUsecase
import com.example.domain.usecases.workingTimeUseCases.UpdateWorkingTimeUseCase
import com.example.shared.Resource
import com.example.touska.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class WorkingTimeViewModel @Inject constructor(
    val getWorkingTimesUsecase: GetWorkingTimesUsecase,
    val createWorkingTimeUseCase: CreateWorkingTimeUseCase,
    val updateWorkingTimeUseCase: UpdateWorkingTimeUseCase,
    val deleteWorkingTimeUseCase: DeleteWorkingTimeUseCase

) : ViewModel() {
    private val workingTimes_ = MutableLiveData<Resource<MutableList<WorkingTime>>>()
    val workingTimes: LiveData<Resource<MutableList<WorkingTime>>> get() = workingTimes_

    private val addWorkingTime_ = MutableLiveData<Resource<WorkingTime>>()
    val addWorkingTime: LiveData<Resource<WorkingTime>> get() = addWorkingTime_

    private val updateWorkingTime_ = MutableLiveData<Resource<Any>>()
    val updateWorkingTime: LiveData<Resource<Any>> get() = updateWorkingTime_


    private val deleteWorkingTime_ = MutableLiveData<Resource<Any>>()
    val deleteWorkingTime : LiveData<Resource<Any>> get() = deleteWorkingTime_


    fun getWorkingTimes() {
        viewModelScope.launch {
            getWorkingTimesUsecase().collect {
                workingTimes_.postValue(it)
            }
        }
    }

    fun createWorkingTime(title: String,startTime:String,endTime:String) {
        if (title.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_working_time_title))
            return
        }
        if (startTime.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_start_time_title))
            return
        }

        if (endTime.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_end_time_title))
            return
        }

        val dateFormat = SimpleDateFormat("HH:mm")
        val startDate = dateFormat.parse(startTime)
        val endDate = dateFormat.parse(endTime)

        if (startDate.compareTo(endDate) >= 0) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.start_time_greater_than_end_time))
            return
        }


        viewModelScope.launch {
            createWorkingTimeUseCase(title,startTime,endTime).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val newList = mutableListOf<WorkingTime>().apply {
                            addAll(workingTimes_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
                            add(resource.result)
                        }
                        workingTimes_.postValue(Resource.Success(newList))
                        addWorkingTime_.postValue(resource)
                    }
                    else -> {
                        addWorkingTime_.postValue(resource)
                    }
                }


            }

        }
    }

    fun updateWorkingTime(title: String,startTime: String,endTime: String,  id: Int) {
        if (title.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_working_time_title))
            return
        }
        if (startTime.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_start_time_title))
            return
        }

        if (endTime.isEmpty()) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.insert_end_time_title))
            return
        }

        val dateFormat = SimpleDateFormat("HH:mm")
        val startDate = dateFormat.parse(startTime)
        val endDate = dateFormat.parse(endTime)

        if (startDate.compareTo(endDate) >= 0) {
            addWorkingTime_.postValue(Resource.Failure("", 0, R.string.start_time_greater_than_end_time))
            return
        }

        viewModelScope.launch {
            updateWorkingTimeUseCase(title,startTime,endTime,id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        workingTimes_.postValue(Resource.Success(resource.result) )
                        updateWorkingTime_.postValue(Resource.Success(true))
                    }
                    else -> {
                        updateWorkingTime_.postValue(resource)
                    }
                }


            }
        }
    }

    fun deleteWorkingTime(id: Int) {
        viewModelScope.launch {
            deleteWorkingTimeUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        workingTimes_.postValue(Resource.Success(resource.result))
                        deleteWorkingTime_.postValue(Resource.Success(true))
                    }
                    else -> {
                        deleteWorkingTime_.postValue(resource)
                    }
                }

            }
        }
    }




}