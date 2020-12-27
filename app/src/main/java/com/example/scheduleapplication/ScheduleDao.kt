package com.example.scheduleapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScheduleDao {

    // データベースへデータを入力
    @Insert
    fun insert(schedule: Schedule)

    // データベースのデータを変更
    @Update
    fun update(schedule: Schedule)

    // データベースのデータを削除
    @Delete
    fun delete(schedule: Schedule)

    // データベースから全データを取得
    @Query("SELECT * FROM schedule_table ORDER BY date ASC")
    fun getAllSchedule(): LiveData<List<Schedule>>

    // データベースから選択した日付の全データを取得
    @Query("SELECT * FROM schedule_table WHERE date = :date ORDER BY startTime ASC")
    fun getDaySchedule(date: String): List<Schedule>

    // データベースからIDに一致するデータを取得
    @Query("SELECT * FROM schedule_table WHERE id=:id")
    fun getIDSchedule(id: Int): Schedule

    // 日付と日付のカウンタ数を取得
    @Query("SELECT date, COUNT(date)AS count FROM schedule_table GROUP BY date ORDER BY date")
    fun getCountDate(): LiveData<List<CountDate>>
}