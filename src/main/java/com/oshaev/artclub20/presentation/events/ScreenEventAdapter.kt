package com.oshaev.artclub20.presentation.events

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.getDimen
import com.oshaev.artclub20.application.setMargin
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ScreenEventAdapter: SimpleListAdapter<EventsRow, ScreenEventAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode == new.hashCode }
    )
) {

    private enum class ViewType {
        TITLE,
        RECYCLER
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EventsTitleRow -> ViewType.TITLE
            is EventsRecyclerRow -> ViewType.RECYCLER
        }.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenEventAdapter.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.TITLE -> {
                TitleViewHolder(EventsScreenTitleView(parent.context)).apply {
                    configure(
                        top = getDimen(R.dimen.event_screen_margin_top),
                        horizontal = getDimen(R.dimen.event_screen_margin_horizontal),
                        bottom = getDimen(R.dimen.event_screen_margin_bottom)
                    )
                }
            }
            ViewType.RECYCLER -> {
                RecycerViewHolder(EventsScreenRecyclerLayout(parent.context)).apply {
                    configure(
                        top = getDimen(R.dimen.event_screen_margin_top),
                        horizontal = getDimen(R.dimen.event_screen_margin_horizontal),
                        bottom = getDimen(R.dimen.event_screen_margin_bottom)
                    )
                }
            }
        }
    }

    inner class TitleViewHolder(val view: EventsScreenTitleView): ViewHolder(view) {

        override fun bind(item: EventsRow) {
            item as EventsTitleRow
            with(view) {
                setData(item.data)
                Log.d("EventAdapter", "title created")

            }
        }
    }

    inner class RecycerViewHolder(val view: EventsScreenRecyclerLayout): ViewHolder(view) {
        override fun bind(item: EventsRow) {
            item as EventsRecyclerRow
            with(view) {
                setData(item.data)
                Log.d("EventAdapter", "recycler created")
            }
        }
    }

    abstract inner class ViewHolder(view: View): SimpleListAdapter<EventsRow, ViewHolder>.ViewHolder(view) {

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
    }

}