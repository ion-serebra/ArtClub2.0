package com.oshaev.artclub20.presentation.studioshedule

import android.util.Log
import android.view.ViewGroup
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ScreenScheduleNewDatesAdapter: SimpleListAdapter<ScreenScheduleDateView.Data, ScreenScheduleNewDatesAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var dateClickListener: ((ScreenScheduleDateView.Data) -> Unit)? = null

    inner class ViewHolder(itemView: ScreenScheduleDateView):
        SimpleListAdapter<ScreenScheduleDateView.Data, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: ScreenScheduleDateView.Data) {
            itemView as ScreenScheduleDateView
            with(itemView) {
                setData(item)
                //setOnClickListener { dateClickListener?.invoke(item) }
                clickListener = dateClickListener
                Log.d("DatesNewAdapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenScheduleNewDatesAdapter.ViewHolder {
        return ViewHolder(ScreenScheduleDateView(parent.context))
    }
}