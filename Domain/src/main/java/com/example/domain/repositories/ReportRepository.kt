package com.example.domain.repositories

import com.example.domain.models.RegisterNeed
import com.example.domain.models.ReportNeed
import com.example.shared.Resource
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun reportNeeds(worker_id:Int): Flow<Resource<ReportNeed>>
}