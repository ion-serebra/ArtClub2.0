package ru.sovcomcard.halva.v1.util.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import ru.sovcomcard.halva.v1.util.ListAdapter

fun ViewGroup.inflate(
    @LayoutRes
    res: Int, attach: Boolean = false
): View =
    LayoutInflater.from(context).inflate(res, this, attach)

fun <DATA, VH: RecyclerView.ViewHolder> ListAdapter<DATA, VH>.safeItemListener(
    adapterPosition: Int?,
    block: ((DATA) -> Unit)?
) {
    adapterPosition?.takeIf { it != RecyclerView.NO_POSITION }?.let {
        block?.invoke(getItem(it))
    }
}


fun RecyclerView.ViewHolder.safeAdapterPosition(block: (Int) -> Unit) {
    adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let {
        block.invoke(it)
    }
}