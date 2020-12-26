package com.example.scheduleapplication

import androidx.lifecycle.LiveData

// データベースへアクセスするリポジトリクラス
class ScheduleRepository(private val scheduleDao: ScheduleDao){

    val allSchedule: LiveData<List<Schedule>> = scheduleDao.getAllSchedule() // 開始日時の昇順で全データを取得
    val countDate:  LiveData<List<CountDate>> = scheduleDao.getCountDate() // 日付と日付のカウンタ数を取得

    // 選択した日付の全データを取得
    suspend fun getDaySchedule(data: String): List<Schedule>{
        return scheduleDao.getDaySchedule(data)
    }

    // IDに一致するデータを取得
    suspend fun getIDSchedule(id: Int): Schedule {
        return scheduleDao.getIDSchedule(id)
    }

    // データベースへデータを入力
    suspend fun insert(schedule: Schedule) {
        scheduleDao.insert(schedule)
    }

    // データベースのデータを削除
    suspend fun delete(schedule: Schedule) {
        scheduleDao.delete(schedule)
    }

    // データベースのデータを変更
    suspend fun update(schedule: Schedule){
        scheduleDao.update(schedule)
    }
}