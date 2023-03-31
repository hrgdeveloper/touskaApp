package com.example.domain.repositories

import com.example.domain.models.Bloc
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface BlocRepository {
    fun getBlocs() : Flow<Resource<MutableList<Bloc>>>
    fun addBloc(name : String) : Flow<Resource<Bloc>>
    fun updateBloc(name : String,id:Int) : Flow<Resource<MutableList<Bloc>>>
    fun deleteBloc(id:Int) : Flow<Resource<MutableList<Bloc>>>
}