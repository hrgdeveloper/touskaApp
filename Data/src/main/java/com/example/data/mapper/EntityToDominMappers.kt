package com.example.data.mapper

import com.example.data.local.room.enteties.*
import com.example.data.network.dtos.ActivityDto
import com.example.data.network.dtos.FloorDto
import com.example.data.network.dtos.UnitDto
import com.example.data.network.dtos.WorkingTimeDto
import com.example.domain.models.*


fun UserEntity.toDomain():User {
    return User(id,contract_type_id,created_at,email,mobile,name,post_id,role_id,status,role,project.toDomain(),
     contractor_id,contractorName,profile
        )
}

fun ProjectEntity.toDomain() : Project{
    return Project(project_id,name,lat, lang,pic)
}

fun BlocEntity.toDomain():Bloc {
  return  Bloc(created_at,id,name,project_id,updated_at,null)
}

fun FloorEntity.toDomain() : Floor {
    return Floor(bloc_id,created_at,id,name,number,updated_at,false,null)
}

fun UnitEntity.toDomain() : Unitt{
    return Unitt(created_at,floor_id,id,name,updated_at)
}


fun PostEntity.toDomain() : Post{
    return Post(id,title)
}

fun ActivityEntity.toDomain() : Activity {
    return Activity(id,title,post_id)
}
fun ContractEntity.toDomain() : Contract{
    return Contract(id,title)
}

fun WorkingTimeEntity.toDomain() : WorkingTime {
    return WorkingTime(id,title,startTime,endTime)
}

fun UserManageEntity.toDomain() : UserManage {
    return UserManage(contract_type_id, created_at, email, email_verified_at, id, mobile, name, post_id, project_id, role_id, status, updated_at,
        profile,contract_type,post_title,qr_code,role_title,
        contractor_id,contractorName
        )
}



