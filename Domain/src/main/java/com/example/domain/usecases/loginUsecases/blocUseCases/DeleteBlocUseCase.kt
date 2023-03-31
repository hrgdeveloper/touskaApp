package com.example.domain.usecases.loginUsecases.blocUseCases

import com.example.domain.models.Bloc
import com.example.domain.repositories.BlocRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBlocUseCase @Inject constructor(val blocRepository: BlocRepository) {

    operator fun invoke(id:Int) : Flow<Resource<MutableList<Bloc>>> {
        return blocRepository.deleteBloc(id)
    }

}