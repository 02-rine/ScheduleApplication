package com.example.scheduleapplication

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

// データベースにアクセスするクラス
class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    // idの昇順で全データを所得
    val allScheduleDate: LiveData<List<Schedule>> = scheduleDao.getAllScheduleData()

    // データの追加
    @WorkerThread
    suspend fun insert(schedule: Schedule){
        scheduleDao.insert(schedule)
    }
}