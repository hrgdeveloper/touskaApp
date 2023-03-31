package com.example.data.mapper

import com.example.data.local.room.enteties.BlocEntity
import com.example.data.local.room.enteties.ProjectEntity
import com.example.data.local.room.enteties.UserEntity
import com.example.data.network.dtos.FloorDto
import com.example.data.network.dtos.UnitDto
import com.example.domain.models.*


fun UserEntity.toDomain():User {
    return User(contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,project.toDomain())
}

fun ProjectEntity.toDomain() : Project{
    return Project(project_id,name,lat, lang,pic)
}

fun BlocEntity.toDomain():Bloc {
  return  Bloc(created_at,floors.map { it.toDomain() },id,name,project_id,updated_at)
}
fun FloorDto.toDomain():Floor{
    return Floor(bloc_id,created_at,id,name,number,
          units.map { it.toDomain() },updated_at
        )
}

fun UnitDto.toDomain():Unitt{
    return Unitt(created_at,floor_id,id,name,updated_at)
}





