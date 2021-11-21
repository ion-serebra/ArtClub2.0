package com.oshaev.artclub20.presentation.events

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.repository.events.EventData

class EventsScreenRecyclerLayout  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var recyclerView: RecyclerView
    val eventsAdapter: EventsListSimpleAdapter = EventsListSimpleAdapter()
    val categoryAdapter: EventAdapter = EventAdapter()

    init {
        View.inflate(context, R.layout.item_event_recycler_view, this)
        recyclerView = findViewById(R.id.event_screen_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context).apply { this.orientation = LinearLayoutManager.HORIZONTAL }
        recyclerView.adapter = eventsAdapter
        setOnClickListener { Log.d("EventsScreenRec", "touched") }
    }

    fun setData(data: List<EventData>) {
        recyclerView.adapter = eventsAdapter
        eventsAdapter.submitList(data)
        Log.d("EventScreenPromos", data.toString())
    }

    // такой костыыль)))
    fun setDataCategory(data: List<EventData>) {
        categoryAdapter.submitList(data)

        recyclerView.adapter = categoryAdapter
        Log.d("EventScreenPromos", data.toString())
    }
}