package com.example.domain.repositories

import com.example.domain.models.RegisterNeed
import com.example.domain.models.Report
import com.example.domain.models.ReportNeed
import com.example.domain.models.ReportNeedFull
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ReportRepository {
    fun reportNeeds(worker_id: Int): Flow<Resource<ReportNeed>>

    fun reportNeedsFull(): Flow<Resource<ReportNeedFull>>

    fun addReport(
        workerId: Int,
        superVisorId: Int,
        activityId: Int,
        blockId: Int,
        floorId: Int?,
        unitId: Int?,
        description: String?,
        times: String,
        pic: File?
    ): Flow<Resource<String>>

    fun getReports(
        blockId: Int?, floorId: Int?, unitId: Int?, superVisorId: Int?,
        workerId: Int?, postId: Int?, activityId: Int?, contractTypeId: Int?,
        startDate: String?, endDate: String?,contractorId:Int?
    ): Flow<Resource<MutableList<Report>>>
}