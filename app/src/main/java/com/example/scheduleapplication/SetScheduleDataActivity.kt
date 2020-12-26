package com.example.scheduleapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.scheduleapplication.databinding.ActivitySetScheduleDataBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_select_schedule_data.*
import kotlinx.android.synthetic.main.activity_set_schedule_data.*
import kotlinx.android.synthetic.main.time_recyclerview_item.*
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// 各データを登録するためのActivity
class SetScheduleDataActivity : AppCompatActivity(), TimePickerFragment.OnTimeSelectedListener {

    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: ActivitySetScheduleDataBinding
    lateinit var schedule: Schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_schedule_data)

        // DataBindingの設定
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_schedule_data)
        binding.schedule = Schedule(0, "", "", "", "", "")
        binding.lifecycleOwner = this

        val scheduleID = intent.getIntExtra("SCHEDULE_ID", 0) // タップされたtimeRecyclerViewのIDを取得
        val date: String? = intent.getStringExtra("SCHEDULE_DATE") // タップされたtimeRecyclerViewの日付を取得
        toolbarText.text = date // toolbarのtoolbarTextに日付を表示

        if(scheduleID != 0){
            // IDが存在する場合、timeRecyclerViewから取得した各データをViewに表示
            scheduleViewModel.setIdSchedule(scheduleID) // タップされたRecyclerViewのIDに一致するデータを取得
            deleteButton.visibility = View.VISIBLE // 削除ボタンの表示
        }else{
            // IDが存在しない場合、現在の時刻をViewに表示
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY) // 初期化する時間
            val minute = c.get(Calendar.MINUTE) // 初期化する分
            schedule = Schedule(0,
                    "",
                    "",
                    date!!,
                    "%1$02d:%2$02d". format(hour, minute),
                    "%1$02d:%2$02d". format(hour, minute))
            binding.schedule = schedule
            deleteButton.visibility = View.INVISIBLE // 削除ボタンの非表示
        }

        scheduleViewModel.idSchedule.observe(this, androidx.lifecycle.Observer {
            binding.schedule = it
        })

        // Viewに入力されたデータの処理
        saveButton.setOnClickListener {
            val replyIntent = Intent()
            val title = titleEdit.text.toString() // Viewからタイトルの取得
            val detail = detailEdit.text.toString() // Viewから詳細の取得
            val startTime: String = startTimeButton.text.toString() // Viewから開始時刻の取得
            val endTime: String = endTimeButton.text.toString() // Viewから終了時刻の取得
            if (scheduleID == 0) {
                // RecyclerViewに新たにデータを作成する処理
                schedule = Schedule(0, title, detail, date!!, startTime, endTime)
                scheduleViewModel.insert(schedule) // データベースにデータを追加
                // スナックバーの表示
                Snackbar.make(it, "追加しました", Snackbar.LENGTH_SHORT)
                        .setAction("戻る") {
                            setResult(RESULT_OK, replyIntent)
                            finish() }
                        .setActionTextColor(Color.WHITE)
                        .show()
            } else {
                // RecyclerViewのデータ内容を変更する処理
                schedule = Schedule(scheduleID, title, detail, date!!, startTime, endTime)
                scheduleViewModel.update(schedule) // データベースの内容を変更
                // スナックバーの表示
                Snackbar.make(it, "修正しました", Snackbar.LENGTH_SHORT)
                        .setAction("戻る") {
                            setResult(RESULT_OK, replyIntent)
                            finish() }
                        .setActionTextColor(Color.WHITE)
                        .show()
            }
        }

        // 削除するデータの処理
        deleteButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("DELETE", "delete")
            schedule = Schedule(scheduleID, "", "", "", "", "")
            scheduleViewModel.delete(schedule) // データベースのIDに一致するデータを削除
            // スナックバーの表示
            Snackbar.make(it, "削除しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") {
                        setResult(RESULT_OK, replyIntent)
                        finish() }
                    .setActionTextColor(Color.WHITE)
                    .show()
        }

        // 開始時点の時刻を設定
        startTimeButton.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager, "startTime_dialog")
        }
        // 終了時点の時刻を設定
        endTimeButton.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager, "endTime_dialog")
        }
    }

    // 時刻選択用ダイアログで時刻が選択された時に呼び出される
    override fun onSelected(hourOfDay: Int, minute: Int) {
        if(supportFragmentManager.findFragmentByTag("startTime_dialog") != null){
            // 開始時点の時刻設定ボタンがクリックされた時の処理
            startTimeButton.text = "%1$02d:%2$02d". format(hourOfDay, minute)
        }else if(supportFragmentManager.findFragmentByTag("endTime_dialog") != null){
            // 終了時点の時刻設定ボタンがクリックされた時の処理
            endTimeButton.text = "%1$02d:%2$02d". format(hourOfDay, minute)
        }
    }
}