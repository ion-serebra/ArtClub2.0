package com.oshaev.artclub20.presentation.studioshedule

import android.util.Log
import android.view.ViewGroup
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class ScreenScheduleCabinetsAdapter:
    SimpleListAdapter<String, ScreenScheduleCabinetsAdapter.ViewHolder>(
        DiffUtilHelper(
            comparatorId = { old, new -> old.javaClass == new.javaClass },
            comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
        )
    ) {

    var dateClickListener: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: ScreenScheduleCabinetView):
        SimpleListAdapter<String, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: String) {
            itemView as ScreenScheduleCabinetView
            with(itemView) {
                setData(item)
                //setOnClickListener { dateClickListener?.invoke(item) }
                clickListener = dateClickListener
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenScheduleCabinetsAdapter.ViewHolder {
        return ViewHolder(ScreenScheduleCabinetView(parent.context))
    }
}