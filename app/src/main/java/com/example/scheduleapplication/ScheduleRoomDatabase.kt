package com.example.scheduleapplication

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleRoomDatabase : RoomDatabase(){

    abstract fun scheduleDao(): ScheduleDao

    // インスタンスの生成を１つだけにする
    companion object{
        @Volatile
        private var INSTANCE: ScheduleRoomDatabase? = null

        // インスタンスを取得
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ScheduleRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScheduleRoomDatabase::class.java,
                    "Schedule_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}