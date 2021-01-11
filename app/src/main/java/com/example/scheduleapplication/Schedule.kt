package com.example.scheduleapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
// データベースの属性データを定義するクラス
data class Schedule(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String?, // タイトル
    @ColumnInfo(name = "detail") val detail: String?, // 説明
    @ColumnInfo(name = "date") val date: String, // 日付
    @ColumnInfo(name = "startTime") val startTime: String, // 開始時刻
    @ColumnInfo(name = "endTime") val endTime: String) // 終了時刻

// データベースの「日付」・「予定件数」を定義するクラス
data class CountDate(
        @ColumnInfo(name = "date") val date: String, // 日付
        @ColumnInfo(name = "count") val count: Int // 日付の予定件数（同じ日付を持つ予定を数え、グループ化する）
)