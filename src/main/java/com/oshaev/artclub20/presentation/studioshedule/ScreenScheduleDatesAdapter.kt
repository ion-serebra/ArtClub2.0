package com.oshaev.artclub20.presentation.studioshedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R

class ScreenScheduleDatesAdapter:
    RecyclerView.Adapter<ScreenScheduleDatesAdapter.ShopsViewHolder>() {

    var onItemClick: ((ScreenScheduleDateView.Data) -> Unit)? = null
    var scheduleList: MutableList<ScreenScheduleDateView.Data> = mutableListOf()

    inner class ShopsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val dayTextView: TextView
        val weekDayText: TextView
        val monthText: TextView
        val loadText: TextView

        init {
            //root = View.inflate(context, R.layout.item_schedule, this)
            dayTextView = itemView.findViewById(R.id.schedule_day_text_view)
            monthText = itemView.findViewById(R.id.schedule_month_text_view)
            weekDayText = itemView.findViewById(R.id.schedule_weekday_text_view)
            loadText = itemView.findViewById(R.id.schedule_dots_text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_schedule_date, parent, false)
        return ShopsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun onBindViewHolder(holder: ShopsViewHolder, position: Int) {
/*        holder.dayTextView.text = scheduleList.get(position).day.toString()
        holder.weekDayText.text = scheduleList.get(position).weekDay
        holder.monthText.text = scheduleList.get(position).month
        var builder: StringBuilder = StringBuilder()
        for (i in 1..scheduleList.get(position).load) {
            builder.append("⚫")
        }
        holder.loadText.setText(builder.toString())
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(scheduleList[position])
            Log.d("DatesAdapter", "clicked")
        }*/
    }

    fun setList(recievedList: List<ScreenScheduleDateView.Data>) {
        scheduleList.clear()
        scheduleList.addAll(recievedList.toMutableList())
        notifyDataSetChanged()
    }
}