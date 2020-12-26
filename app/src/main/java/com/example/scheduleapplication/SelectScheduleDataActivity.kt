package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_select_schedule_data.*
import kotlinx.android.synthetic.main.day_recyclerview_item.*

class SelectScheduleDataActivity : AppCompatActivity() {

    private val scheduleViewModel: ScheduleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_schedule_data)

        // timeRecyclerViewの設定
        val adapter = TimeScheduleListAdapter(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        timeRecyclerView.adapter = adapter
        timeRecyclerView.addItemDecoration(itemDecoration)
        timeRecyclerView.layoutManager = LinearLayoutManager(this) // 各画面部品を縦に並べる

        val date = intent.getStringExtra("SCHEDULE_DATE") // タップされた日付を取得
        toolbar_text.text = date // ツールバーのテキストに日付を表示

        scheduleViewModel.setDaySchedule(date!!)
        // タップされた日付のScheduleデータをtimeRecyclerViewに表示
        scheduleViewModel.daySchedule.observe(this, Observer {
            adapter.setSchedule(it)
        })

        // timeRecyclerViewをクリック時、SetScheduleDataActivityに画面遷移
        adapter.setOnItemClickListener { schedule ->
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("SCHEDULE_ID", schedule.id) // タップされたセルのIDをSetScheduleDataActivityに送る
            intent.putExtra("SCHEDULE_DATE", date)
            startActivityForResult(intent, RequestCode)
        }

        // SetScheduleDataActivityに画面遷移
        fab.setOnClickListener {
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("SCHEDULE_DATE", date)
            startActivityForResult(intent, RequestCode)
        }
    }

    companion object{
        const val RequestCode = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode && resultCode == Activity.RESULT_OK) {
            val date = intent.getStringExtra("SCHEDULE_DATE") // タップされた日付を取得
            scheduleViewModel.setDaySchedule(date!!)
        }
    }
}