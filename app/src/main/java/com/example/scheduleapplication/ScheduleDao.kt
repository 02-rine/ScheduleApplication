package com.example.scheduleapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScheduleDao {

    // データベースへデータを入力
    @Insert
    fun insert(schedule: Schedule)

    // schedule_table表の削除
    @Query("DELETE FROM schedule_table")
    fun deleteAll()

    // データベースの全データを取得
    @Query("SELECT * FROM schedule_table")
    fun getAllSchedule(): LiveData<List<Schedule>>
}