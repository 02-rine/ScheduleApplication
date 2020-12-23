package com.example.scheduleapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_set_schedule_data.*

class SetScheduleDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_schedule_data)
        val replyIntent = Intent()

        saveButton.setOnClickListener{
            if(TextUtils.isEmpty(titleEdit.text)){
                // titleEditが未入力の時の処理
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{
                // titleEditに文字が入力された時の処理
                val title = titleEdit.text.toString()
                replyIntent.putExtra("INPUT_TITLE", title)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}