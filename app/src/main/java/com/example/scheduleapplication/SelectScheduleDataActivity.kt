package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        // RecyclerViewの設定
        val adapter = TimeScheduleListAdapter(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        timeRecyclerView.adapter = adapter
        timeRecyclerView.addItemDecoration(itemDecoration)
        timeRecyclerView.layoutManager = LinearLayoutManager(this) // 各画面部品を縦に並べる

        val date =  intent.getStringExtra("DATE") // タップされた日付を取得
        toolbar_text.text = date // ツールバーのテキストに日付を表示

        scheduleViewModel.setDaySchedule(date!!)
        // タップされた日付のScheduleデータをRecyclerViewに表示
        scheduleViewModel.daySchedule.observe(this, Observer {
            adapter.setSchedule(it)
        })

        // RecyclerViewをクリック時、SetScheduleDataActivityに画面遷移
        adapter.setOnItemClickListener { schedule ->
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("SCHEDULE_ID", schedule.id) // タップされたセルのIDをSetScheduleDataActivityに送る
            intent.putExtra("DATE", date)
            startActivityForResult(intent, REQUEST_CODE)
        }

        // SetScheduleDataActivityに画面遷移
        fab.setOnClickListener {
            val intent = Intent(this@SelectScheduleDataActivity, SetScheduleDataActivity::class.java)
            intent.putExtra("DATE", date)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    companion object {
        const val REQUEST_CODE = 1 // SetScheduleDataActivityを起動するときのリクエストコードの定義
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val date =  intent.getStringExtra("DATE")
        scheduleViewModel.setDaySchedule(date!!)
        // SetScheduleDataActivityからの正常な処理を行った場合
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.getStringExtra("DELETE") == "delete") {
                Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            }
        } else {
            // SetScheduleDataActivityのタイトルが未入力の場合
            Toast.makeText(applicationContext, "保存できませんでした", Toast.LENGTH_SHORT).show()
        }
    }
}