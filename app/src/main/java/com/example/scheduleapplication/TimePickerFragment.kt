package com.example.scheduleapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

// 時刻用ダイアログ
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener{

    // インターフェイスをアクティビティに実装する
    // アクティビティでonSelectedメソッドに時刻が選択された時の処理を記述する
    interface OnTimeSelectedListener{
        fun onSelected(hourOfDay: Int, minute: Int)
    }

    private var listener : OnTimeSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is OnTimeSelectedListener -> listener = context
        }
    }

    // 時刻選択用ダイアログを作成
    // TimePickerDialogのインスタンスを返す
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 現在の時刻を初期値として設定
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY) // 初期化する時
        val minute = c.get(Calendar.MINUTE) // 初期化する分
        return TimePickerDialog(context, this, hour, minute, true)
    }

    // TimePickerDialogで時刻の
    // 選択時に呼び出される
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener?.onSelected(hourOfDay, minute)
    }
}