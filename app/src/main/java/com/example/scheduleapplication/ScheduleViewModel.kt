package com.example.scheduleapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// データの保持・管理をするクラス
class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    // 非同期処理をするための宣言
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ScheduleRepository
    val allSchedule: LiveData<List<Schedule>> // データベースの全データを格納
    val countDate:  LiveData<List<CountDate>> // データベースの日付と日付のカウント数を格納
    val daySchedule = MutableLiveData<List<Schedule>>() // データベースから選択した日付の全データを格納
    var idSchedule = MutableLiveData<Schedule>() // データベースから選択したIDのデータを格納

    init{
        val scheduleDao = ScheduleRoomDatabase.getDatabase(application, scope).scheduleDao()
        repository = ScheduleRepository(scheduleDao)
        allSchedule = repository.allSchedule // データベースの全データを取得
        countDate = repository.countDate // データベースの日付と日付のカウント数を取得
    }

    // データベースから選択した日付の全データを取得
    fun setDaySchedule(data: String) = scope.launch(Dispatchers.IO){
        daySchedule.postValue(repository.getDaySchedule(data))
    }

    // データベースからIDに一致するデータを取得
    fun setIdSchedule(id: Int) = scope.launch(Dispatchers.IO) {
        idSchedule.postValue(repository.getIDSchedule(id))
    }

    // データベースへデータを入力
    fun insert(schedule: Schedule) = scope.launch(Dispatchers.IO) {
        repository.insert(schedule)
    }

    // データベースのデータを削除
    fun delete(schedule: Schedule) = scope.launch(Dispatchers.IO){
        repository.delete(schedule)
    }

    // データベースのデータを変更
    fun update(schedule: Schedule) = scope.launch(Dispatchers.IO){
        repository.update(schedule)
    }

    // parentJobをキャンセル
    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}