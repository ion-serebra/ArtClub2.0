package com.oshaev.artclub20.presentation.studioshedule

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R

class ScreenScheduleCabinetsRecyclerView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val cabinetsRecyclerView: RecyclerView
    val cabinetsAdapter: ScreenScheduleCabinetsAdapter = ScreenScheduleCabinetsAdapter()
    var onDateClick: ((String) -> Unit)? = null

    init {
        View.inflate(context, R.layout.item_schedule_cabinets_recycler_view, this)
        cabinetsRecyclerView = findViewById(R.id.schedule_cabinets_recycler_view)
        val manager = LinearLayoutManager(this.context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        cabinetsRecyclerView.layoutManager = manager
        cabinetsRecyclerView.adapter = cabinetsAdapter
    }

    fun setData(data: Data) {
        cabinetsAdapter.submitList(data.cabinetsList)
        cabinetsRecyclerView.adapter = cabinetsAdapter
        cabinetsAdapter.dateClickListener = { onDateClick?.invoke(it) }
        cabinetsAdapter.notifyDataSetChanged()
        Log.d("DatesRecycler", data.toString())
    }

    data class Data(
        val id: String,
        val cabinetsList: List<String>
    )
}