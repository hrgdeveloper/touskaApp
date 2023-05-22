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

class AddReportUseCase @Inject constructor(val reportRepository: ReportRepository) {
    operator fun invoke( workerId: Int,
                         superVisorId: Int,
                         activityId: Int,
                         blockId: Int,
                         floorId: Int?,
                         unitId: Int?,
                         description: String?,
                         times: String,
                         pic:File?
    ): Flow<Resource<String> > {
        return reportRepository.addReport(workerId, superVisorId, activityId, blockId, floorId, unitId, description, times,pic)
    }

}