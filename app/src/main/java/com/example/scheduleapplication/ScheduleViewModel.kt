package com.example.scheduleapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

// データの保持・管理をするクラス
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    // 非同期処理をするための宣言
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ScheduleRepository // Repositoryの宣言
    val allScheduleData: LiveData<List<Schedule>> // idの昇順で全データを格納

    init{
        val scheduleDao = ScheduleRoomDatabase.getDatabase(application, scope).scheduleDao()
        repository = ScheduleRepository(scheduleDao) // Repositoryクラスのインスタンスを生成
        allScheduleData = repository.allScheduleData // idの昇順で全データを取得
    }

    // parentJobをキャンセル
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun insert(schedule: Schedule) = scope.launch(Dispatchers.IO) {
        repository.insert(schedule)
    }
}