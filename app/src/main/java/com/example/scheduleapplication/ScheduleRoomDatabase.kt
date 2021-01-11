package com.example.scheduleapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

// Roomにより、データベースにアクセスする
@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleRoomDatabase : RoomDatabase() {

    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: ScheduleRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ScheduleRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Room databaseを取得
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