package com.example.domain.repositories

import com.example.domain.models.*
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface ContractRepository {
    fun getContracts() : Flow<Resource<MutableList<Contract>>>
    fun addContract(title:String) :  Flow<Resource<Contract>>
    fun updateContract(title : String,id:Int) : Flow<Resource<MutableList<Contract>>>
    fun deleteContract(id:Int) : Flow<Resource<MutableList<Contract>>>

}