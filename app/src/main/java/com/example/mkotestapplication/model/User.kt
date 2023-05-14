package com.example.mkotestapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    val name: String
) {
    @PrimaryKey
    var id: Int = 12345
}