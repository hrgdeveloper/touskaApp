package com.example.data.mapper

import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.*
import com.example.domain.models.User

fun UserDto.toEntity() : UserEntity {
    return UserEntity(id,contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,
           project.toEntity(),contractor_id
        )
}
fun ProjectDto.toEntity():ProjectEntity {
    return ProjectEntity(id,name,lat, lng,pic)
}


fun BlocDto.toEntity():BlocEntity {
    return BlocEntity(created_at,id,name,project_id,updated_at)
}

fun FloorDto.toEntity() : FloorEntity {
    return FloorEntity(bloc_id,created_at,id,name,number,updated_at)
}

fun UnitDto.toEntity() : UnitEntity {
    return UnitEntity(created_at,floor_id,id,name,updated_at)
}
fun PostDto.toEntity() : PostEntity {
    return PostEntity(id,title)
}

fun ActivityDto.toEntity() : ActivityEntity {
    return ActivityEntity(id,title,post_id)
}

fun ContractDto.toEntity() : ContractEntity {
    return ContractEntity(id,title)
}
fun WorkingTimeDto.toEntity() : WorkingTimeEntity {
    return WorkingTimeEntity(id,title,startTime,endTime)
}
fun UserManageDto.toEntity() : UserManageEntity {
    return UserManageEntity(contractTypeId, createdAt, email, emailVerifiedAt, id, mobile, name, postId, projectId, roleId, status, updatedAt,
          profile,contractType,postTitle,qrCode,roleTitle
        )
}
