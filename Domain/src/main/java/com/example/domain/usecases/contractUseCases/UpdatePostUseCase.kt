package com.example.domain.usecases.contractUseCases

import com.example.domain.models.Contract
import com.example.domain.models.Post
import com.example.domain.models.Unitt
import com.example.domain.repositories.ContractRepository
import com.example.domain.repositories.PostRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateContractUseCase @Inject constructor(val contractRepository: ContractRepository) {

    operator fun invoke(title:String, id:Int) : Flow<Resource<MutableList<Contract>>> {
        return contractRepository.updateContract(title,id)
    }

}