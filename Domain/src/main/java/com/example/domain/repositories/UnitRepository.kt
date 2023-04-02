package com.example.domain.repositories

import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.domain.models.Unitt
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface UnitRepository {
    fun getUnits(floor_id:Int) : Flow<Resource<MutableList<Unitt>>>
    fun addUnit(floor_id: Int,name:String) :  Flow<Resource<Unitt>>
    fun updateUnit(name : String,id:Int) : Flow<Resource<MutableList<Unitt>>>
    fun deleteUnit(id:Int) : Flow<Resource<MutableList<Unitt>>>

}