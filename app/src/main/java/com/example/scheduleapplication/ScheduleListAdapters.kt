package com.example.scheduleapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// RecyclerViewのAdapter用のファイル

// 予定一覧を表示する表（dayRecyclerView）のAdapter
class DayScheduleListAdapter(context: Context
) : RecyclerView.Adapter<DayScheduleListAdapter.DayScheduleViewHolder>() {

    private var listener: ((CountDate) -> Unit)? = null
    fun setOnItemClickListener(listener: (CountDate)-> Unit){
        this.listener = listener
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var countDate = emptyList<CountDate>() // 「日付」と「日付の予定件数」を格納

    inner class DayScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var dateText: TextView = itemView.findViewById(R.id.dateText) // 「日付」
        var countDateText: TextView = itemView.findViewById(R.id.countDateText) // 「日付の予定件数」
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayScheduleListAdapter.DayScheduleViewHolder{
        val itemView = inflater.inflate(R.layout.day_recyclerview_item, parent, false)
        return DayScheduleViewHolder(itemView)
    }

    // 表（DayScheduleViewHolder）の各セルに表示データを割り当てる
    override fun onBindViewHolder(holder: DayScheduleListAdapter.DayScheduleViewHolder, position: Int) {
        val countDateItem = countDate[position]
        holder.dateText.text = countDateItem.date // 「日付」を表示
        holder.countDateText.text = "予定件数 : " + countDateItem.count + "件" // 「日付の予定件数」を表示
        // セルがタップされたことを予定の追加・変更画面（MainActivity）へ通知する
        holder.itemView.setOnClickListener{
            listener?.invoke(countDateItem)
        }
    }

    // データ件数を返すメソッド
    override fun getItemCount() = countDate.size

    // countDateのセッター
    fun setSchedule(countDate: List<CountDate>){
        this.countDate = countDate
        notifyDataSetChanged()
    }
}

// 登録された予定の表（timeRecyclerView）のAdapter
class TimeScheduleListAdapter(context: Context
) : RecyclerView.Adapter<TimeScheduleListAdapter.TimeScheduleViewHolder>(){

    private var listener: ((Schedule) -> Unit)? = null
    fun setOnItemClickListener(listener: (Schedule)-> Unit){
        this.listener = listener
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var schedule = emptyList<Schedule>() // Scheduleのリストを格納

    inner class TimeScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var starTimeText: TextView = itemView.findViewById(R.id.startTime) // 「開始時刻」
        var endTimeText: TextView = itemView.findViewById(R.id.endTime) // 「終了時刻」
        var titleText: TextView = itemView.findViewById(R.id.titleText) // 「タイトル」
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeScheduleListAdapter.TimeScheduleViewHolder {
        val itemView = inflater.inflate(R.layout.time_recyclerview_item, parent, false)
        return TimeScheduleViewHolder(itemView)
    }

    // 表（TimeScheduleViewHolder）の各セルに表示データを割り当てるメソッド
    override fun onBindViewHolder(holder: TimeScheduleListAdapter.TimeScheduleViewHolder, position: Int) {
        val scheduleItem = schedule[position]
        holder.starTimeText.text = scheduleItem.startTime // 「開始時刻」を表示
        holder.endTimeText.text = scheduleItem.endTime // 「終了時刻」を表示
        holder.titleText.text = scheduleItem.title // 「タイトル」を表示
        // セルがタップされたことを予定の追加・変更画面（SelectScheduleDataActivity）へ通知する
        holder.itemView.setOnClickListener{
            listener?.invoke(scheduleItem)
        }
    }

    // データ件数を返すメソッド
    override fun getItemCount() = schedule.size

    // scheduleのセッター
    fun setSchedule(schedule: List<Schedule>){
        this.schedule = schedule
        notifyDataSetChanged() // データセットが変更されたことをobserverに通知
    }
}