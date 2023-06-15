package com.example.domain.usecases.reportUseCase

import com.example.domain.models.RegisterNeed
import com.example.domain.models.Report
import com.example.domain.models.ReportNeed
import com.example.domain.models.UserManage
import com.example.domain.repositories.ReportRepository
import com.example.domain.repositories.UserManageRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(val reportRepository: ReportRepository) {
    operator fun invoke(blockId: Int?,floorId: Int?,unitId: Int?,superVisorId: Int?,
                        workerId: Int?,postId:Int?,activityId: Int?,contractTypeId:Int?,
                        startDate:String?,endDate:String?,contractorId:Int?,orderBy:String,orderType:String
    ): Flow<Resource<MutableList<Report>>>{
        return reportRepository.getReports(blockId, floorId, unitId, superVisorId, workerId, postId, activityId, contractTypeId, startDate, endDate,contractorId,
            orderBy,orderType
            )
    }

}