package com.esammahdi.yemekcalendar.core.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "institution")
data class InstitutionEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String = "",
)
