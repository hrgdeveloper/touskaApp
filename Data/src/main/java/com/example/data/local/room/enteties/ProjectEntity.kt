package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectEntity(@PrimaryKey(autoGenerate = false) val project_id : Int,
                         val name:String,val lat : Double,val lang :Double,val pic : String
                   )
