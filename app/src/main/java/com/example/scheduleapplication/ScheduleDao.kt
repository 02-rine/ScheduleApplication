package com.example.scheduleapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScheduleDao {

    @Insert
    fun insert(schedule: Schedule)

    // schedule_table表の削除
    @Query("DELETE FROM schedule_table")
    fun deleteAll()

    // idの昇順で全データを所得
    @Query("SELECT * FROM schedule_table ORDER BY id ASC")
    fun getAllScheduleData(): LiveData<List<Schedule>>
}