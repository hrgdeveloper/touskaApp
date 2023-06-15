package com.example.touska.screens.reportScreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.*
import com.example.domain.usecases.loginUsecases.blocUseCases.BlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.CreateBlocUsecase
import com.example.domain.usecases.loginUsecases.blocUseCases.DeleteBlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.UpdateBlocUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.CreateFloorUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.DeleteFloorUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.GetFloorsUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.UpdateFloorUseCase
import com.example.domain.usecases.reportUseCase.GetReportsUseCase
import com.example.domain.usecases.reportUseCase.ReportNeedsFullUseCase
import com.example.domain.usecases.reportUseCase.ReportNeedsUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.example.touska.utils.DataProvider
import com.example.touska.utils.FilterModel
import com.example.touska.utils.FilterTypes
import com.example.touska.utils.requestValue
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    val getReports: GetReportsUseCase,
    val reportNeedsFullUseCase: ReportNeedsFullUseCase,
    private val application: Application


) : ViewModel() {

    private val _reportsNeeds = MutableLiveData<Resource<ReportNeedFull>>()
    val reportsNeeds: LiveData<Resource<ReportNeedFull>> get() = _reportsNeeds

    private val reports_ = MutableLiveData<Resource<MutableList<Report>>>()
    val reports: LiveData<Resource<MutableList<Report>>> get() = reports_

    val filtersList = mutableStateListOf<FilterModel>()

    var bottomSheetType = mutableStateOf(1)

    var sortList = mutableStateListOf<Sort>()



    var blockName = mutableStateOf("")
    var floorName = mutableStateOf("")
    var unitName = mutableStateOf("")
    var superVisorName = mutableStateOf("")
    var postTitle = mutableStateOf("")
    var activityName = mutableStateOf("")
    var contractTypeName = mutableStateOf("")
    var workerName = mutableStateOf("")
    var contractorName = mutableStateOf("")
    var orderBy = mutableStateOf("submitted")
    var orderType = mutableStateOf("asc")


    var blockId = mutableStateOf(0)
    var floorId = mutableStateOf(0)
    var unitId = mutableStateOf(0)
    var superVisorId = mutableStateOf(0)
    var postId = mutableStateOf(0)
    var activityId = mutableStateOf(0)
    var contractTypeId = mutableStateOf(0)
    var workerId = mutableStateOf(0)
    var contractorId = mutableStateOf(0)


    var startDatePersian = mutableStateOf("")
    var endDatePersian = mutableStateOf("")
    var startDate = mutableStateOf("")
    var endDate = mutableStateOf("")

    init {
        sortList.addAll(DataProvider.sortList())
    }

    fun fetchReports() {
        viewModelScope.launch {
            getReports(blockId.value.requestValue(),floorId.value.requestValue(), unitId.value.requestValue(),
                superVisorId.value.requestValue(),workerId.value.requestValue(),postId.value.requestValue(),
                activityId.value.requestValue(),contractTypeId.value.requestValue(),startDate.value.requestValue(),
                endDate.value.requestValue(),contractorId.value.requestValue(),
                orderBy.value,orderType.value
                 ).collect {
                reports_.postValue(it)
            }
        }
    }

    fun fetchReportNeeds(){
        viewModelScope.launch {
            reportNeedsFullUseCase().collect {
                _reportsNeeds.postValue(it)
            }
        }
    }

    fun addToFilterList(filterModel: FilterModel) {
        if (!filtersList.contains(filterModel)) {
            filtersList.add(filterModel)
        }
    }
    fun removeFromFilterList(filterModel: FilterModel) {
          filtersList.remove(filterModel)
          if (filterModel.filterType==FilterTypes.BLOCK.type) {
              blockName.value=""
              blockId.value=0
          }else if (filterModel.filterType==FilterTypes.FLOOR.type) {
              floorName.value=""
              floorId.value=0
          }
          else if (filterModel.filterType==FilterTypes.UNIT.type) {
              unitName.value=""
              unitId.value=0
          }
          else if (filterModel.filterType==FilterTypes.POST.type) {
              postTitle.value=""
              postId.value=0
          }
          else if (filterModel.filterType==FilterTypes.ACTIVITY.type) {
              activityName.value=""
              activityId.value=0
          }else if (filterModel.filterType==FilterTypes.CONTRACT.type) {
              contractTypeName.value=""
              contractTypeId.value=0
          }else if (filterModel.filterType==FilterTypes.START_TIME.type) {
              startDatePersian.value=""
              startDate.value=""
          }else if (filterModel.filterType==FilterTypes.END_TIME.type){
              endDate.value=""
              endDatePersian.value=""
          }else if (filterModel.filterType==FilterTypes.Contractor.type){
              contractorName.value=""
              contractorId.value=0
          }
    }





}