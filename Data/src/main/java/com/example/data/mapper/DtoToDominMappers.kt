package com.example.data.mapper

import com.example.data.network.dtos.*
import com.example.domain.models.*


fun RegisterNeedDto.toDomain(): RegisterNeed {
    return RegisterNeed(posts.map { it.toDomain() }, contracts.map { it.toDomain() })
}

fun ReportNeedDto.toDomain(): ReportNeed {
    return ReportNeed(
        activities.map { it.toDomain() },
        blocs.map { it.toDomain() },
        workingTimes.map { it.toDomain() }
        )
}

fun PostDto.toDomain(): Post {
    return Post(id, title)
}

fun ContractDto.toDomain(): Contract {
    return Contract(id, title)
}

fun ActivityDto.toDomain(): Activity {
    return Activity(id, title, post_id)
}

fun BlocDto.toDomain(): Bloc {
    return Bloc(created_at, id, name, project_id, updated_at,floors.map { it.toDomain() })
}

fun FloorDto.toDomain(): Floor {
    return Floor(bloc_id, created_at, id, name, number, updated_at, unit = units.map { it.toDomain() })
}

fun UnitDto.toDomain(): Unitt {
    return Unitt(created_at, floor_id, id, name, updated_at)
}

fun WorkingTimeDto.toDomain():WorkingTime {
    return WorkingTime(id, title, startTime, endTime)
}

