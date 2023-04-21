package com.example.touska.screens.reportScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.usecases.loginUsecases.blocUseCases.BlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.CreateBlocUsecase
import com.example.domain.usecases.loginUsecases.blocUseCases.DeleteBlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.UpdateBlocUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.CreateFloorUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.DeleteFloorUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.GetFloorsUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.UpdateFloorUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
//    val getFloorsUsecase: GetFloorsUsecase,
//    val createFloorUsecase: CreateFloorUsecase,
//    val updateFloorUseCase: UpdateFloorUseCase,
//    val deleteFloorUseCase: DeleteFloorUseCase

) : ViewModel() {


//    private val floors_ = MutableLiveData<Resource<MutableList<Floor>>>()
//    val floors: LiveData<Resource<MutableList<Floor>>> get() = floors_
//
//    private val addFloor_ = MutableLiveData<Resource<Floor>>()
//    val addFloor: LiveData<Resource<Floor>> get() = addFloor_
//
//    private val updateFloor_ = MutableLiveData<Resource<Any>>()
//    val updateFloor: LiveData<Resource<Any>> get() = updateFloor_
//
//
//    private val deleteFloor_ = MutableLiveData<Resource<Any>>()
//    val deleteFloor: LiveData<Resource<Any>> get() = deleteFloor_
//
//
//    fun getFloors(bloc_id: Int) {
//        viewModelScope.launch {
//            getFloorsUsecase(bloc_id).collect {
//                floors_.postValue(it)
//            }
//        }
//    }
//
//    fun createFloor(name: String, number: Int, bloc_id: Int) {
//        if (name.isEmpty()) {
//            addFloor_.postValue(Resource.Failure("", 0, R.string.insert_floor_name))
//            return
//        }
//        viewModelScope.launch {
//            createFloorUsecase(name, bloc_id, number).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        val newFloorList = mutableListOf<Floor>().apply {
//                            addAll(floors_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
//                            add(resource.result)
//                        }
//                        floors_.postValue(Resource.Success(newFloorList))
//                        addFloor_.postValue(resource)
//                    }
//                    else -> {
//                        addFloor_.postValue(resource)
//                    }
//                }
//
//
//            }
//
//        }
//    }
//
//    fun updateFloor(name: String, number: Int, id: Int, bloc_id: Int) {
//        if (name.isEmpty()) {
//            updateFloor_.postValue(Resource.Failure("", 0, R.string.insert_bloc_name))
//            return
//        }
//        viewModelScope.launch {
//            updateFloorUseCase(name, number, id, bloc_id).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        floors_.postValue(Resource.Success(resource.result) )
//                        updateFloor_.postValue(Resource.Success(true))
//                    }
//                    else -> {
//                        updateFloor_.postValue(resource)
//                    }
//                }
//
//
//            }
//        }
//    }
//
//    fun deleteBloc(id: Int, bloc_id: Int) {
//        viewModelScope.launch {
//            deleteFloorUseCase(id, bloc_id).collect { resource ->
//                when (resource) {
//                    is Resource.Success -> {
//                        floors_.postValue(Resource.Success(resource.result))
//                        deleteFloor_.postValue(Resource.Success(true))
//                    }
//                    else -> {
//                        deleteFloor_.postValue(resource)
//                    }
//                }
//
//            }
//        }
//    }




}