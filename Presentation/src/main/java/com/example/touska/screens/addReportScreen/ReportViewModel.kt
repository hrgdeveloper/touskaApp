package com.example.touska.screens.addReportScreen

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mapper.toDto
import com.example.data.utils.FileUtils
import com.example.domain.models.RegisterNeed
import com.example.domain.models.ReportNeed
import com.example.domain.models.WorkingTime
import com.example.domain.usecases.reportUseCase.AddReportUseCase
import com.example.domain.usecases.reportUseCase.ReportNeedsUseCase

import com.example.domain.usecases.usermanageUseCase.RegisterNeedsUseCase
import com.example.domain.usecases.usermanageUseCase.RegisterUsersUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.utils.isValidEmail
import com.google.gson.Gson

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    val reportNeedsUseCase: ReportNeedsUseCase,
    val addReportUseCase: AddReportUseCase

) : ViewModel() {
    private val needs_ = MutableLiveData<Resource<ReportNeed>>()
    val needs: LiveData<Resource<ReportNeed>> get() = needs_


    private val addReport_ = MutableLiveData<Resource<String>>()
    val addReport: LiveData<Resource<String>> get() = addReport_


    fun getReportNeeds(worker_id: Int) {
        viewModelScope.launch {
            reportNeedsUseCase(worker_id).collect {
                needs_.postValue(it)
            }
        }
    }

    fun submitReport(
        workerId: Int,
        superVisorId: Int,
        activityId: Int,
        blockId: Int,
        floorId: Int,
        unitId: Int,
        description: String,
        times: List<WorkingTime>,
        picUri:Uri?,
        context:Context,
        submitted_at : String?
    ) {
        var pic : File?=null
        picUri?.let {
            pic= FileUtils.getFile(context,picUri)
        }

        if (activityId == 0) {
            addReport_.postValue(Resource.Failure("", 400, R.string.insert_activity_name))
            return
        }
        if (blockId == 0) {
            addReport_.postValue(Resource.Failure("", 400, R.string.insert_bloc_name))
            return
        }

        if (times.isEmpty()) {
            addReport_.postValue(Resource.Failure("", 400, R.string.insert_adlast_one_time))
            return
        }
        viewModelScope.launch {
            addReportUseCase(
                workerId,
                superVisorId,
                activityId,
                blockId, if (floorId == 0) null else floorId,
                if (unitId == 0) null else unitId,
                if (description.isEmpty()) null else description,
                Gson().toJson(times.map { it.toDto() }),
                pic,submitted_at
            ).collect {
                addReport_.postValue(it)
            }
        }

    }


}