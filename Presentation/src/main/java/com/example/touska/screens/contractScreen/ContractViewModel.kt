package com.example.touska.screens.contractScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Contract
import com.example.domain.models.Post
import com.example.domain.usecases.contractUseCases.CreateContractUsecase
import com.example.domain.usecases.contractUseCases.DeleteContractUseCase
import com.example.domain.usecases.contractUseCases.GetContractsUsecase
import com.example.domain.usecases.contractUseCases.UpdateContractUseCase
import com.example.domain.usecases.loginUsecases.postUseCases.CreatePostUsecase
import com.example.domain.usecases.loginUsecases.postUseCases.DeletePostUseCase
import com.example.domain.usecases.loginUsecases.postUseCases.GetPostsUsecase
import com.example.domain.usecases.loginUsecases.postUseCases.UpdatePostUseCase
import com.example.shared.Resource
import com.example.touska.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContractViewModel @Inject constructor(
    val getContractsUsecase: GetContractsUsecase,
    val createContractUsecase: CreateContractUsecase,
    val updateContractUseCase: UpdateContractUseCase,
    val deleteContractUseCase: DeleteContractUseCase

) : ViewModel() {
    private val contracts_ = MutableLiveData<Resource<MutableList<Contract>>>()
    val contracts: LiveData<Resource<MutableList<Contract>>> get() = contracts_

    private val addContract_ = MutableLiveData<Resource<Contract>>()
    val addContract: LiveData<Resource<Contract>> get() = addContract_

    private val updateContract_ = MutableLiveData<Resource<Any>>()
    val updateContract: LiveData<Resource<Any>> get() = updateContract_


    private val deleteContract_ = MutableLiveData<Resource<Any>>()
    val deleteContract: LiveData<Resource<Any>> get() = deleteContract_


    fun getContracts() {
        viewModelScope.launch {
            getContractsUsecase().collect {
                contracts_.postValue(it)
            }
        }
    }

    fun createContract(title: String) {
        if (title.isEmpty()) {
            addContract_.postValue(Resource.Failure("", 0, R.string.insert_contract_name))
            return
        }
        viewModelScope.launch {
            createContractUsecase(title).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val newUnitList = mutableListOf<Contract>().apply {
                            addAll(contracts_.value?.let { ((it as Resource.Success).result) } ?: mutableListOf())
                            add(resource.result)
                        }
                        contracts_.postValue(Resource.Success(newUnitList))
                        addContract_.postValue(resource)
                    }
                    else -> {
                        addContract_.postValue(resource)
                    }
                }


            }

        }
    }

    fun updateContract(title: String, id: Int) {
        if (title.isEmpty()) {
            updateContract_.postValue(Resource.Failure("", 0, R.string.insert_contract_name))
            return
        }
        viewModelScope.launch {
            updateContractUseCase(title, id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        contracts_.postValue(Resource.Success(resource.result) )
                        updateContract_.postValue(Resource.Success(true))
                    }
                    else -> {
                        updateContract_.postValue(resource)
                    }
                }


            }
        }
    }

    fun deleteContract(id: Int) {
        viewModelScope.launch {
            deleteContractUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        contracts_.postValue(Resource.Success(resource.result))
                        deleteContract_.postValue(Resource.Success(true))
                    }
                    else -> {
                        deleteContract_.postValue(resource)
                    }
                }

            }
        }
    }

}