package com.example.scheduleapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int, // ID（主キー）
    @ColumnInfo(name = "date") val date: String, // 日付
    @ColumnInfo(name = "title") val title: String, // タイトル
    @ColumnInfo(name = "detail") val detail: String?) // 詳細