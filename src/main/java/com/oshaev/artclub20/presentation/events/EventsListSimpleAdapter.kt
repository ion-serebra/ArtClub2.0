package com.oshaev.artclub20.presentation.events

import android.util.Log
import android.view.ViewGroup
import com.oshaev.artclub20.presentation.mybookings.MyBookingKIRView
import com.oshaev.artclub20.repository.events.EventData
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class EventsListSimpleAdapter: SimpleListAdapter<EventData, EventsListSimpleAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var dateClickListener: ((EventData) -> Unit)? = null

    inner class ViewHolder(itemView: EventView):
        SimpleListAdapter<EventData, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: EventData) {
            itemView as EventView
            with(itemView) {
                setData(item)
                Log.d("DatesNewAdapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListSimpleAdapter.ViewHolder {
        return ViewHolder(EventView(parent.context))
    }
}