package com.example.data.mapper

import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.*
import com.example.domain.models.User

fun UserDto.toEntity() : UserEntity {
    return UserEntity(id,contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,
           project.toEntity()
        )
}
fun ProjectDto.toEntity():ProjectEntity {
    return ProjectEntity(id,name,lat, lng,pic)
}


fun BlocDto.toEntitiy():BlocEntity {
    return BlocEntity(created_at,id,name,project_id,updated_at)
}

fun FloorDto.toEntity() : FloorEntity {
    return FloorEntity(bloc_id,created_at,id,name,number,updated_at)
}

fun UnitDto.toEntity() : UnitEntity {
    return UnitEntity(created_at,floor_id,id,name,updated_at)
}
