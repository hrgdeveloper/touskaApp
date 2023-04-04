package com.example.domain.usecases.contractUseCases

import com.example.domain.models.Contract
import com.example.domain.repositories.ContractRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContractsUsecase @Inject constructor(val contractRepository: ContractRepository) {

    operator fun invoke() : Flow<Resource<MutableList<Contract>>> {
        return contractRepository.getContracts()
    }

}