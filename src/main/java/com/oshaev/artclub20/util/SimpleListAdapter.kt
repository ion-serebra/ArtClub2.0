package ru.sovcomcard.halva.v1.util

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.sovcomcard.halva.v1.util.recyclerView.safeItemListener

abstract class SimpleListAdapter<DATA, VH>(diffCallback: DiffUtil.ItemCallback<DATA>):
    ListAdapter<DATA, VH>(diffCallback) where VH: SimpleListAdapter<DATA, VH>.ViewHolder {

    constructor(
        id: DATA.() -> Any?,
        hashCode: DATA.() -> Int
    ): this(DiffUtilHelper(id, hashCode))

    var onItemClickListener: ((DATA) -> Unit)? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    abstract inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                safeItemListener(adapterPosition, onItemClickListener)
            }
        }

        abstract fun bind(item: DATA)

        fun setForeground(animated: Boolean) {
            if (animated) {
                // itemView.setSelectableForeground()
            } else {
                //itemView.removeForeground()
            }
        }
    }


}