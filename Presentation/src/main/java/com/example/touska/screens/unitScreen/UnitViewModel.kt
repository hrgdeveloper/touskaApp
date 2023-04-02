package com.example.touska.screens.unitScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.models.Unitt
import com.example.domain.usecases.loginUsecases.blocUseCases.BlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.CreateBlocUsecase
import com.example.domain.usecases.loginUsecases.blocUseCases.DeleteBlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.UpdateBlocUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.CreateFloorUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.DeleteFloorUseCase
import com.example.domain.usecases.loginUsecases.floorUsecases.GetFloorsUsecase
import com.example.domain.usecases.loginUsecases.floorUsecases.UpdateFloorUseCase
import com.example.domain.usecases.loginUsecases.unitUseCases.CreateUnitUsecase
import com.example.domain.usecases.loginUsecases.unitUseCases.DeleteUnitUseCase
import com.example.domain.usecases.loginUsecases.unitUseCases.GetUnitsUsecase
import com.example.domain.usecases.loginUsecases.unitUseCases.UpdateUnitUseCase
import com.example.shared.Resource
import com.example.touska.R
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnitViewModel @Inject constructor(
    val getUnitsUsecase: GetUnitsUsecase,
    val createUnitUsecase: CreateUnitUsecase,
    val updateUnitUseCase: UpdateUnitUseCase,
    val deleteUnitUseCase: DeleteUnitUseCase

) : ViewModel() {
    private val units_ = MutableLiveData<Resource<MutableList<Unitt>>>()
    val units: LiveData<Resource<MutableList<Unitt>>> get() = units_

    private val addUnit_ = MutableLiveData<Resource<Unitt>>()
    val addUnit: LiveData<Resource<Unitt>> get() = addUnit_

    private val updateUnit_ = MutableLiveData<Resource<Any>>()
    val updateUnit: LiveData<Resource<Any>> get() = updateUnit_


    private val deleteUnit_ = MutableLiveData<Resource<Any>>()
    val deleteUnit: LiveData<Resource<Any>> get() = deleteUnit_


    fun getUnits(floor_id: Int) {
        viewModelScope.launch {
            getUnitsUsecase(floor_id).collect {
                units_.postValue(it)
            }
        }
    }

    fun createUnit(name: String, floor_id: Int) {
        if (name.isEmpty()) {
            addUnit_.postValue(Resource.Failure("", 0, R.string.insert_unit_name))
            return
        }
        viewModelScope.launch {
            createUnitUsecase(name,floor_id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val newUnitList = mutableListOf<Unitt>().apply {
                            addAll(units_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
                            add(resource.result)
                        }
                        units_.postValue(Resource.Success(newUnitList))
                        addUnit_.postValue(resource)
                    }
                    else -> {
                        addUnit_.postValue(resource)
                    }
                }


            }

        }
    }

    fun updateUnit(name: String, id: Int) {
        if (name.isEmpty()) {
            updateUnit_.postValue(Resource.Failure("", 0, R.string.insert_unit_name))
            return
        }
        viewModelScope.launch {
            updateUnitUseCase(name, id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        units_.postValue(Resource.Success(resource.result) )
                        updateUnit_.postValue(Resource.Success(true))
                    }
                    else -> {
                        updateUnit_.postValue(resource)
                    }
                }


            }
        }
    }

    fun deleteUnit(id: Int) {
        viewModelScope.launch {
            deleteUnitUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        units_.postValue(Resource.Success(resource.result))
                        deleteUnit_.postValue(Resource.Success(true))
                    }
                    else -> {
                        deleteUnit_.postValue(resource)
                    }
                }

            }
        }
    }




}