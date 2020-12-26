package com.example.scheduleapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
// データベースの属性データを持つクラス
data class Schedule(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String?, // タイトル
    @ColumnInfo(name = "detail") val detail: String?, // 詳細
    @ColumnInfo(name = "date") val date: String, // 日付
    @ColumnInfo(name = "startTime") val startTime: String, // 開始の時刻
    @ColumnInfo(name = "endTime") val endTime: String) // 終了の時刻

// データベースの日付と日付のカウンタ数を持つクラス
data class CountDate(
        @ColumnInfo(name = "date") val date: String, // 日付
        @ColumnInfo(name = "count") val count: Int // 日付のカウンタ数
)