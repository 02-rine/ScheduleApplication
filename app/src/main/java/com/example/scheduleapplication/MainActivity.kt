package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerViewの設定
        val adapter = ScheduleListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) // 各画面部品を縦に並べる
        // RecyclerViewをクリック時、SetScheduleDataActivityに画面遷移
        adapter.setOnItemClickListener { id ->
            val intent = Intent(this@MainActivity, SetScheduleDataActivity::class.java)
                .putExtra("SCHEDULE_ID", id) // タップされたセルのIDをSetScheduleDataActivityに送る
            startActivityForResult(intent, REQUEST_CODE)
        }

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        // データセットが変更された時に呼ばれる
        scheduleViewModel.allSchedule.observe(this, Observer { schedule ->
            schedule?.let {
                adapter.setSchedule(it)
            }
        })

        // SetScheduleDataActivityに画面遷移
        fab.setOnClickListener{
            val intent = Intent(this@MainActivity, SetScheduleDataActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }
    companion object{
        const val REQUEST_CODE = 1 // SetScheduleDataActivityを起動するときのリクエストコードの定義
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // SetScheduleDataActivityのタイトルが入力された場合
            val schedule = Schedule(0, "noDate", data?.getStringExtra("INPUT_TITLE")!!, "noData")
            scheduleViewModel.insert(schedule)
        } else {
            // SetScheduleDataActivityのタイトルが未入力の場合
            Toast.makeText(applicationContext, "保存できませんでした", Toast.LENGTH_SHORT).show()
        }
    }
}