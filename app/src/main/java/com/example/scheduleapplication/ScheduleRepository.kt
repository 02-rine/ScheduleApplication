package com.example.scheduleapplication

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// データベースへアクセスリポジトリ
class ScheduleRepository(private val scheduleDao: ScheduleDao){

    // データベースの全データを取得する
    val allSchedule: LiveData<List<Schedule>> = scheduleDao.getAllSchedule( )

    // データベースへデータを入力する
    @WorkerThread
    suspend fun insert(schedule: Schedule) {
        scheduleDao.insert(schedule)
    }
}