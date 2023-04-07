package com.example.domain.repositories

import com.example.domain.models.*
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface WorkingTimeRepository {
    fun getWorkingTimes() : Flow<Resource<MutableList<WorkingTime>>>
    fun addWorkingTime(title:String,startTime:String,endTime:String) :  Flow<Resource<WorkingTime>>
    fun updateWorkingTime(title : String,
                          startTime: String,
                          endTime: String,
                          id:Int) : Flow<Resource<MutableList<WorkingTime>>>
    fun deleteWorkingTime(id:Int) : Flow<Resource<MutableList<WorkingTime>>>

}