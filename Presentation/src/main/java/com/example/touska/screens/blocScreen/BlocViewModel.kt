package com.example.touska.screens.blocScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Bloc
import com.example.domain.usecases.loginUsecases.blocUseCases.BlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.CreateBlocUsecase
import com.example.domain.usecases.loginUsecases.blocUseCases.DeleteBlocUseCase
import com.example.domain.usecases.loginUsecases.blocUseCases.UpdateBlocUseCase
import com.example.shared.Resource
import com.example.touska.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlocViewModel @Inject constructor(val blocUseCase: BlocUseCase,
                                        val createBlocUsecase: CreateBlocUsecase,
                                        val updateBlocUseCase: UpdateBlocUseCase,
                                        val deleteBlocUseCase: DeleteBlocUseCase
                                        ) : ViewModel() {
   private  val blocs_ = MutableLiveData<Resource<MutableList<Bloc>>>()
   val blocs  : LiveData<Resource<MutableList<Bloc>>>  get() = blocs_

    private  val addBloc_ = MutableLiveData<Resource<Bloc>>()
    val addBloc  : LiveData<Resource<Bloc>>  get() = addBloc_

    private  val updateBloc_ = MutableLiveData<Resource<Any>>()
    val updateBloc  : LiveData<Resource<Any>>  get() = updateBloc_


    private  val deleteBloc_ = MutableLiveData<Resource<Any>>()
    val deleteBloc  : LiveData<Resource<Any>>  get() = deleteBloc_


    fun getBlocs(){
        viewModelScope.launch {
            blocUseCase().collect {
                blocs_.postValue(it)
            }
        }
    }

    fun createBloc(name:String) {
        if (name.isEmpty()) {
            addBloc_.postValue(Resource.Failure("",0,R.string.insert_bloc_name))
            return
        }

        viewModelScope.launch {
            createBlocUsecase(name).collect {resource->
                when (resource) {
                    is Resource.Success -> {
                        val newBlocsList = mutableListOf<Bloc>().apply {
                            addAll(blocs_.value?.let { ((it as Resource.Success).result)} ?: mutableListOf())
                            add(resource.result)
                        }
                        blocs_.postValue(Resource.Success(newBlocsList))
                        addBloc_.postValue(resource)
                    }
                    else -> {
                        addBloc_.postValue(resource)
                    }
                }



            }

        }
    }

    fun updateBloc(name:String,id:Int) {
        if (name.isEmpty()) {
            updateBloc_.postValue(Resource.Failure("",0,R.string.insert_bloc_name))
            return
        }
        viewModelScope.launch {
            updateBlocUseCase(name,id).collect {resource->
                when (resource) {
                    is Resource.Success -> {
                        blocs_.postValue(Resource.Success(resource.result))
                        updateBloc_.postValue(Resource.Success(true))
                    }
                    else -> {
                        updateBloc_.postValue(resource)
                    }
                }


            }
        }
    }

    fun deleteBloc(id:Int) {
        viewModelScope.launch {
            deleteBlocUseCase(id).collect {resource->
                when (resource) {
                    is Resource.Success -> {
                        blocs_.postValue(Resource.Success(resource.result))
                        deleteBloc_.postValue(Resource.Success(true))
                    }
                    else -> {
                        deleteBloc_.postValue(resource)
                    }
                }

            }
        }
    }



}