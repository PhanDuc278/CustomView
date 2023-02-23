package com.example.training_android.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "value1")
    val value1: String,

    @ColumnInfo(name = "value2")
    val value2: String,

    @ColumnInfo(name = "value3")
    val value3: String
) : java.io.Serializable