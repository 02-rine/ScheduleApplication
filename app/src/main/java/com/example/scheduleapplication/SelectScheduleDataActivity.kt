package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select_schedule_data.*
import kotlinx.android.synthetic.main.activity_set_schedule_data.*
import kotlinx.android.synthetic.main.day_recyclerview_item.*

/*
予定の追加・変更画面
日付選択画面（MainActivity）から渡された「日付」に新たに予定を追加するか、既存の予定内容を変更するかを選択する
 */
class SelectScheduleDataActivity : AppCompatActivity() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_schedule_data)

        // 登録された予定の表（timeRecyclerView）の設定
        // 各セルには登録された予定の「タイトル」・「開始の時刻」・「終了の時刻」を表示
        val adapter = TimeScheduleListAdapter(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        timeRecyclerView.adapter = adapter
        timeRecyclerView.addItemDecoration(itemDecoration)
        timeRecyclerView.layoutManager = LinearLayoutManager(this) // 各画面部品を縦に並べる

        // ツールバーのタイトルに、日付選択画面（MainActivity）でタップされた「日付」を表示
        val date = intent.getStringExtra("SCHEDULE_DATE") // タップされた「日付」を取得
        setSupportActionBar(selectActivityToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 戻るメニューの表示
        supportActionBar?.title = ""
        selectActivityToolbarText.text = date // ツールバーのタイトルに「日付」を表示

        // 日付選択画面（MainActivity）でタップされた「日付」に一致するデータをデータベースから取得し、表（timeRecyclerView）に表示する
        scheduleViewModel.setDaySchedule(date!!)
        scheduleViewModel.daySchedule.observe(this, Observer {
            adapter.setSchedule(it)
        })

        // 既存の予定内容を変更するために、予定の登録画面（SetScheduleDataActivity）に画面遷移
        // タップされた表（timeRecyclerView）のセルの「ID」・「日付」を渡す
        adapter.setOnItemClickListener { schedule ->
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("SCHEDULE_ID", schedule.id) // タップされたセルの「ID」を渡す
            intent.putExtra("SCHEDULE_DATE", date) // 「日付」を渡す
            startActivityForResult(intent, RequestCode)
        }

        // 新たに予定を追加するために、予定の登録画面（SetScheduleDataActivity）に画面遷移
        // タップされたセルの「日付」を渡す
        fab.setOnClickListener {
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("SCHEDULE_DATE", date) // 「日付」を渡す
            startActivityForResult(intent, RequestCode)
        }
    }

    companion object{
        const val RequestCode = 1
    }

    // ツールバーの戻るメニューを押した時、Activityを終了
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // 予定の登録画面（SetScheduleDataActivity）により予定が追加・変更されたことを表（timeRecyclerView）に反映する
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK) {
            val date = intent.getStringExtra("SCHEDULE_DATE") // タップされた「日付」を取得
            scheduleViewModel.setDaySchedule(date!!) // 表（timeRecyclerView）の再表示
        }
    }
}