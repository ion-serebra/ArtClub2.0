package com.oshaev.artclub20.presentation.mybookings

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.application.setMargin
import com.oshaev.artclub20.repository.BookingModel
import com.oshaev.artclub20.repository.CommentModel
import ru.sovcomcard.halva.v1.util.DiffUtilHelper
import ru.sovcomcard.halva.v1.util.SimpleListAdapter

class MyBookingsAdapter: SimpleListAdapter<BookingModel, MyBookingsAdapter.ViewHolder>(
    DiffUtilHelper(
        comparatorId = { old, new -> old.javaClass == new.javaClass && old.id == new.id },
        comparatorHash = { old, new -> old.hashCode() == new.hashCode() }
    )
) {

    var dateClickListener: ((BookingModel) -> Unit)? = null
    var commentSend: ((CommentModel) -> Unit)? = null
    var acceptedClicked: ((BookingModel) -> Unit)? = null
    var removeClicked: ((BookingModel) -> Unit)? = null

    inner class ViewHolder(itemView: MyBookingView):
        SimpleListAdapter<BookingModel, ViewHolder>.ViewHolder(itemView) {

        override fun bind(item: BookingModel) {
            itemView as MyBookingView
            with(itemView) {
                setData(item)
                clickListener = dateClickListener
                onCommentSend = commentSend
                onAccepted = acceptedClicked
                onRemove = { itemView.visibility = View.GONE}
                onRemove = removeClicked
                Log.d("DatesNewAdapter", item.toString())
                itemView.layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
                itemView.setMargin(
                    horizontal = 16,
                    top = 0,
                    bottom = 0
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingsAdapter.ViewHolder {
        return ViewHolder(MyBookingView(parent.context))
    }
}