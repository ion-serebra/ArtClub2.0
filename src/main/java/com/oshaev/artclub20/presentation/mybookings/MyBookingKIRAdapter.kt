package com.oshaev.artclub20.presentation.mybookings

import android.util.Log
import android.view.ViewGroup
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class MyBookingKIRAdapter: SimpleListAdapter<MyBookingKIRView.Data, MyBookingKIRAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var dateClickListener: ((MyBookingKIRView.Data) -> Unit)? = null

    inner class ViewHolder(itemView: MyBookingKIRView):
        SimpleListAdapter<MyBookingKIRView.Data, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: MyBookingKIRView.Data) {
            itemView as MyBookingKIRView
            with(itemView) {
                setData(item)
                //setOnClickListener { dateClickListener?.invoke(item) }
                clickListener = dateClickListener

                Log.d("DatesNewAdapter", item.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingKIRAdapter.ViewHolder {
        return ViewHolder(MyBookingKIRView(parent.context))
    }
}