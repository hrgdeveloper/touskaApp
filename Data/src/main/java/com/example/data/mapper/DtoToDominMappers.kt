package com.example.data.mapper

import com.example.data.network.dtos.*
import com.example.data.utils.convertGregorianToPersian
import com.example.domain.models.*

fun UserDto.toDomain() : User{
  return  User(id,contract_type_id,created_at,email,mobile,name,post_id,role_id,
          status,role,null,contractor_id,contractorName,profile
        )
}

fun RegisterNeedDto.toDomain(): RegisterNeed {
    return RegisterNeed(posts.map { it.toDomain() }, contracts.map { it.toDomain() },
           contractors.map { it.toDomain() }
        )
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
    return Bloc(created_at, id, name, project_id, updated_at, floors?.map { it.toDomain() })
}

fun FloorDto.toDomain(): Floor {
    return Floor(
        bloc_id,
        created_at,
        id,
        name,
        number,
        updated_at,
        unit = units?.map { it.toDomain() })
}

fun UnitDto.toDomain(): Unitt {
    return Unitt(created_at, floor_id, id, name, updated_at)
}

fun WorkingTimeDto.toDomain(): WorkingTime {
    return WorkingTime(id, title, startTime, endTime)
}

fun ReportDto.toDomain(): Report {
    return Report(
        activity,
        activity_id,
        bloc_id,
        block_name,
        contract_type,
        created_at,
        deleted_at,
        description,
        floor_id,
        floor_name,
        id,
        post,
        post_id,
        report_times.map { it.toDomain() },
        submitted.convertGregorianToPersian(),
        supervisor_id,
        total_time,
        unit_id,
        unit_name,
        updated_at,
        worker_id,
        worker_name,
        supervisorName,
        contractorName,
        pic
    )
}


fun ReportTimeDto.toDomain(): ReportTime {
    return ReportTime(created_at, end_time, id, report_id, start_time, updated_at)
}

fun ReportNeedFullDto.toDomain(): ReportNeedFull {
    return ReportNeedFull(activities.map { it.toDomain() },
        blocs.map { it.toDomain() }, contracts.map { it.toDomain() },
        posts.map { it.toDomain() }, floors.map { it.toDomain() },
        units.map { it.toDomain() },
        contractors.map { it.toDomain() }
    )
}

