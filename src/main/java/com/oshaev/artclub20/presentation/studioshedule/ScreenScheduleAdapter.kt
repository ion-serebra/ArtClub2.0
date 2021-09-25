package com.oshaev.artclub20.presentation.studioshedule

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.application.makeVisibleOrGone
import com.oshaev.artclub20.application.setMargin
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ScreenScheduleAdapter: SimpleListAdapter<ScheduleRow, ScreenScheduleAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode == new.hashCode }
    )
) {

    var onDateItemClick: ((ScreenScheduleDateView.Data) -> Unit)? = null
    var onCabinetItemClick: ((String) -> Unit)? = null

    //visibility settings:
    private var datesVisibility = true
    private var bookingVisibility = true
    private var titleVisibility = true

    private enum class ViewType {
        TITLE,
        BOOKING,
        DATES,
        CABINETS,
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ScheduleTitleRow -> ViewType.TITLE
            is ScheduleBookingRow -> ViewType.BOOKING
            is ScheduleDatesRow -> ViewType.DATES
            is ScheduleCabinetsRow -> ViewType.CABINETS
            else -> TODO()
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TITLE -> {
                TitleViewHolder(ScreenScheduleTitleView(parent.context)).apply {
                    configure(
                        top = 16,
                        horizontal = 16,
                        bottom = 0,
                    )
                    setVisibility(titleVisibility)
                }
            }
            ViewType.BOOKING -> {
                BookingViewHolder(ScreenScheduleBookingView(parent.context)).apply {
                    configure(
                        top = 0,
                        horizontal = 16,
                        bottom = 0,
                    )
                    setVisibility(bookingVisibility)
                }
            }
            ViewType.DATES -> {
                DatesViewHolder(ScreenScheduleDatesRecyclerView(parent.context)).apply {
                    configure(
                        top = 8,
                        horizontal = 16,
                        bottom = 0,
                    )
                    setVisibility(datesVisibility)
                }
            }
            ViewType.CABINETS -> {
                CabinetsViewHolder(ScreenScheduleCabinetsRecyclerView(parent.context)).apply {
                    configure(
                        top = 8,
                        horizontal = 16,
                        bottom = 0,
                    )
                }
            }
        }
    }

    inner class TitleViewHolder(val view: ScreenScheduleTitleView): ViewHolder(view) {

        override fun bind(item: ScheduleRow) {
            item as ScheduleTitleRow
            view as ScreenScheduleTitleView
            view.setData(item.data)
        }
    }

    inner class BookingViewHolder(val view: ScreenScheduleBookingView): ViewHolder(view) {

        override fun bind(item: ScheduleRow) {
            item as ScheduleBookingRow
            view as ScreenScheduleBookingView
            view.setData(item.data)
        }
    }

    inner class DatesViewHolder(val view: ScreenScheduleDatesRecyclerView): ViewHolder(view) {

        override fun bind(item: ScheduleRow) {
            item as ScheduleDatesRow
            view as ScreenScheduleDatesRecyclerView
            view.setData(item.data)
            view.onDateClick = onDateItemClick
        }
    }

    inner class CabinetsViewHolder(val view: ScreenScheduleCabinetsRecyclerView): ViewHolder(view) {

        override fun bind(item: ScheduleRow) {
            item as ScheduleCabinetsRow
            view as ScreenScheduleCabinetsRecyclerView
            view.setData(item.data)
            view.onDateClick = onCabinetItemClick
        }
    }

    abstract inner class ViewHolder(view: View): SimpleListAdapter<ScheduleRow, ViewHolder>.ViewHolder(view) {

        fun configure(top: Int = 0, bottom: Int = 0, horizontal: Int = 0, animated: Boolean = true) {
            setForeground(animated)

            itemView.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            itemView.setMargin(
                horizontal = horizontal,
                top = top,
                bottom = bottom
            )
        }

        fun setVisibility(isVisible: Boolean) {
            itemView.makeVisibleOrGone(isVisible)
        }
    }

    fun isDatesVisible(isVisible: Boolean) {
        datesVisibility = isVisible
        notifyDataSetChanged()
        Log.d("ScheduleAdapter", "Dates visibility: " + isVisible.toString())
    }

    fun isBookingVisible(isVisible: Boolean) {
        bookingVisibility = isVisible
        notifyDataSetChanged()
    }

    fun isTitleVisible(isVisible: Boolean) {
        titleVisibility = isVisible
        notifyDataSetChanged()
    }
}