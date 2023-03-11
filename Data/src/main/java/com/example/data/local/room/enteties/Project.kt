package com.example.data.local.room.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project(@PrimaryKey(autoGenerate = false) val project_id : Int,
                  val name:String
                   )
