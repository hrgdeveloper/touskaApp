package com.example.domain.repositories

import com.example.domain.models.Bloc
import com.example.domain.models.Floor
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface FloorRepository {
    fun getFloors(bloc_id:Int) :  Flow<Resource<MutableList<Floor>>>

    fun addFloor(bloc_id:Int,name:String,number:Int) :  Flow<Resource<Floor>>

    fun updateFloor(name : String, number:Int,  id:Int,bloc_id: Int) : Flow<Resource<MutableList<Floor>>>

    fun deleteBloc(id:Int,bloc_id: Int) : Flow<Resource<MutableList<Floor>>>

}