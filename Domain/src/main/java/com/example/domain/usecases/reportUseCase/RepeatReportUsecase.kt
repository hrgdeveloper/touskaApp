package com.example.domain.usecases.reportUseCase

import com.example.domain.repositories.ReportRepository
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepeatReportUsecase @Inject constructor(val reportRepository: ReportRepository) {
    operator fun invoke(
        reportId:Int
    ): Flow<Resource<String>> {
        return reportRepository.repeatReport(
            reportId
        )
    }

}