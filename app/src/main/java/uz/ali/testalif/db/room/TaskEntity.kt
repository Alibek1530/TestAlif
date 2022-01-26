package com.tsm.roomdaggerhilt.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "alarmTime") val alarmTime: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "dateMilli") val dateMilli: Long,
    @ColumnInfo(name = "timeMille") val timeMille: Long
)