package com.example.scheduleapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import org.w3c.dom.ls.LSException

// RecyclerViewのAdapter用のファイル

// １日ごとのスケジュールをdayRecyclerViewに並べるときに使用するAdapter
class DayScheduleListAdapter(context: Context
) : RecyclerView.Adapter<DayScheduleListAdapter.DayScheduleViewHolder>() {

    private var listener: ((CountDate) -> Unit)? = null
    // 関数型の変数Listenerに登録する
    fun setOnItemClickListener(listener: (CountDate)-> Unit){
        this.listener = listener
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var countDate = emptyList<CountDate>() // 日付と日付のカウンタ数を格納

    inner class DayScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var dateText: TextView = itemView.findViewById(R.id.dateText) // 日付
        var countDateText: TextView = itemView.findViewById(R.id.countDateText) // 日付のカウンタ数
    }

    // DayScheduleViewHolderのオブジェクト生成をするメソッド
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayScheduleListAdapter.DayScheduleViewHolder{
        val itemView = inflater.inflate(R.layout.day_recyclerview_item, parent, false)
        return DayScheduleViewHolder(itemView)
    }

    // DayScheduleViewHolder内の各セルに表示データを割り当てるメソッド
    override fun onBindViewHolder(holder: DayScheduleListAdapter.DayScheduleViewHolder, position: Int) {
        val countDateItem = countDate[position]
        holder.dateText.text = countDateItem.date // 日付を表示
        holder.countDateText.text = "登録件数 : " + countDateItem.count + "件" // 日付のカウンタ数を表示
        // dayRecyclerViewがタップされたことをMainActivityへ通知する
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

// 時間ごとのスケジュールをRecyclerViewに並べるときに使用するAdapter
class TimeScheduleListAdapter(context: Context
) : RecyclerView.Adapter<TimeScheduleListAdapter.TimeScheduleViewHolder>(){

    private var listener: ((Schedule) -> Unit)? = null
    // 関数型の変数Listenerに登録する
    fun setOnItemClickListener(listener: (Schedule)-> Unit){
        this.listener = listener
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var schedule = emptyList<Schedule>()

    inner class TimeScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var starTimeText: TextView = itemView.findViewById(R.id.starTimeText)
        var endTimeText: TextView = itemView.findViewById(R.id.endTimeText)
        var titleText: TextView = itemView.findViewById(R.id.titleText)
    }

    // ScheduleViewHolderのオブジェクト生成をするメソッド
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeScheduleListAdapter.TimeScheduleViewHolder {
        val itemView = inflater.inflate(R.layout.time_recyclerview_item, parent, false)
        return TimeScheduleViewHolder(itemView)
    }

    // ScheduleViewHolder内の各画面部品に表示データを割り当てるメソッド
    override fun onBindViewHolder(holder: TimeScheduleListAdapter.TimeScheduleViewHolder, position: Int) {
        val scheduleItem = schedule[position]
        holder.starTimeText.text = scheduleItem.startTime
        holder.endTimeText.text = scheduleItem.endTime
        holder.titleText.text = scheduleItem.title
        // RecyclerViewのがタップされたことをMainActivityへ通知する
        holder.itemView.setOnClickListener{
            // タップされたセルのID・タイトル・日時を送る
            listener?.invoke(scheduleItem)
        }
    }

    // データ件数を返すメソッド
    override fun getItemCount() = schedule.size

    // scheduleのセッター
    fun setSchedule(schedule: List<Schedule>){
        this.schedule = schedule
        notifyDataSetChanged() // データセットが変更されたことを登録されている全てのobserverに通知
    }
}