package com.example.scheduleapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String?, // タイトル
    @ColumnInfo(name = "detail") val detail: String?, // 詳細
    @ColumnInfo(name = "startDate") val startDate: String, // 開始の日付
    @ColumnInfo(name = "startTime") val startTime: String, // 開始の時刻
    @ColumnInfo(name = "endDate") val endDate: String, // 終了の日付
    @ColumnInfo(name = "endTime") val endTime: String) // 終了の時刻