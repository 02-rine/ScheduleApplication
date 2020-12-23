package com.example.scheduleapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey @ColumnInfo(name = "title") val title: String) // タイトル