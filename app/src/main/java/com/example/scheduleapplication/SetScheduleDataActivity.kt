package com.example.scheduleapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.scheduleapplication.databinding.ActivitySetScheduleDataBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_set_schedule_data.*
import java.util.*

/*
予定の登録画面
データベースに予定の「タイトル」・「開始時刻」・「終了時刻」・「日付」・「説明」を書き込む
保存ボタンにより、予定内容を書き込む
削除ボタンにより、既存の予定を削除する
 */
class SetScheduleDataActivity : AppCompatActivity(), TimePickerFragment.OnTimeSelectedListener {

    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private lateinit var binding: ActivitySetScheduleDataBinding
    lateinit var schedule: Schedule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_schedule_data)

        // DataBindingの初期設定を行う
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_schedule_data)
        binding.schedule = Schedule(0, "", "", "", "", "")
        binding.lifecycleOwner = this


        // 予定の追加・変更画面（SelectScheduleDataActivity）から「ID」・「日付」を取得
        // 予定を新たに追加する時は、ID = 0（データベースに新たにデータを追加）
        // 予定内容を変更するときは、ID = 設定済みのID
        val scheduleID = intent.getIntExtra("SCHEDULE_ID", 0) // 「ID」の取得
        val date: String? = intent.getStringExtra("SCHEDULE_DATE") // 「日付」の取得

        // ツールバーのタイトルに、日付選択画面（MainActivity）でタップされた「日付」を表示
        setSupportActionBar(setActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 戻るメニューの追加
        supportActionBar?.title = ""
        setActivityToolbarText.text = date // ツールバーのタイトルに「日付」を表示

        if(scheduleID != 0){
            // 「ID」が存在する場合、登録された予定の表（timeRecyclerView）から取得した予定内容をViewに表示
            scheduleViewModel.setIdSchedule(scheduleID) // データベースから「ID」に一致するデータを取得
            deleteButton.visibility = View.VISIBLE // 削除ボタンの表示
        }else{
            // 「ID」が存在しない場合、現在の時刻をViewに表示
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

        // データベースから「ID」に一致するデータをViewに表示
        // scheduleViewModel.setIdSchedule(scheduleID)によりデータを取得する
        scheduleViewModel.idSchedule.observe(this, androidx.lifecycle.Observer {
            binding.schedule = it
        })

        // 保存ボタンを押すことで、Viewに入力されたデータをデータベースに登録する
        saveButton.setOnClickListener {
            val replyIntent = Intent()
            val title = titleEdit.text.toString() // Viewから「タイトル」の取得
            val detail = detailEdit.text.toString() // Viewから「説明」の取得
            val startTime: String = startTimeText.text.toString() // Viewから「開始時刻」の取得
            val endTime: String = endTimeText.text.toString() // Viewから「終了時刻」の取得

            if (scheduleID == 0) {
                // データベースに新たにデータを追加する
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
                // データベースの予定内容を更新する
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

        // データベースから削除する予定データの処理
        deleteButton.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra("DELETE", "delete")
            schedule = Schedule(scheduleID, "", "", "", "", "")
            scheduleViewModel.delete(schedule) // データベースの「ID」に一致する予定データを削除
            // スナックバーの表示
            Snackbar.make(it, "削除しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") {
                        setResult(RESULT_OK, replyIntent)
                        finish() }
                    .setActionTextColor(Color.WHITE)
                    .show()
        }

        // 「開始時刻」を時刻選択ダイアログにより設定
        startTimeText.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager, "startTime_dialog")
        }
        // 「終了時刻」を時刻選択ダイアログにより設定
        endTimeText.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(supportFragmentManager, "endTime_dialog")
        }
    }

    // ツールバーの戻るメニューを押した時、Activityを終了
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // 時刻選択用ダイアログで時刻が選択された時に呼び出される
    override fun onSelected(hourOfDay: Int, minute: Int) {
        if(supportFragmentManager.findFragmentByTag("startTime_dialog") != null){
            // 「開始時刻」の時刻設定ボタンがクリックされた時の処理
            startTimeText.text = "%1$02d:%2$02d". format(hourOfDay, minute)
        }else if(supportFragmentManager.findFragmentByTag("endTime_dialog") != null){
            // 「終了時刻」の時刻設定ボタンがクリックされた時の処理
            endTimeText.text = "%1$02d:%2$02d". format(hourOfDay, minute)
        }
    }
}