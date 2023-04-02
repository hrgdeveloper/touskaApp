package com.example.domain.usecases.loginUsecases.unitUseCases

import com.example.domain.models.Floor
import com.example.domain.models.Unitt
import com.example.domain.repositories.FloorRepository
import com.example.domain.repositories.UnitRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUnitUseCase @Inject constructor(val unitRepository: UnitRepository) {

    operator fun invoke(name:String, id:Int) : Flow<Resource<MutableList<Unitt>>> {
        return unitRepository.updateUnit(name,id)
    }

}