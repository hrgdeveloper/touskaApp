package com.example.domain.usecases.reportUseCase

import com.example.domain.models.RegisterNeed
import com.example.domain.models.ReportNeed
import com.example.domain.models.UserManage
import com.example.domain.repositories.ReportRepository
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class ReportNeedsUseCase @Inject constructor(val reportRepository: ReportRepository) {
    operator fun invoke(worker_id:Int
    ): Flow<Resource<ReportNeed> > {
        return reportRepository.reportNeeds(worker_id)
    }

}