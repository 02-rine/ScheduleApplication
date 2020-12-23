package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_set_schedule_data.*

// 各データを登録するためのActivity
class SetScheduleDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_schedule_data)

        // 入力されたデータの処理を行う
        saveButton.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleEdit.text)) {
                // タイトルが未入力の場合
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                // タイトルが入力された場合
                val word = titleEdit.text.toString()
                replyIntent.putExtra("INPUT_TITLE", word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}