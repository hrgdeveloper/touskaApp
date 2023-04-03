package com.example.domain.repositories

import com.example.domain.models.*
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    fun getActivities(post_id: Int) : Flow<Resource<MutableList<Activity>>>
    fun addActivity(title:String,post_id:Int) :  Flow<Resource<Activity>>
    fun updateActivity(title : String,id:Int) : Flow<Resource<MutableList<Activity>>>
    fun deleteActivity(id:Int) : Flow<Resource<MutableList<Activity>>>

}