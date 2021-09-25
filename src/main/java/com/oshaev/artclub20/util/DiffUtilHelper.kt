package ru.sovcomcard.halva.v1.util

import androidx.recyclerview.widget.DiffUtil

class DiffUtilHelper<ROW>(
    val comparatorId: ((ROW, ROW) -> Boolean),
    val comparatorHash: ((ROW, ROW) -> Boolean)
): DiffUtil.ItemCallback<ROW>() {

    companion object {

        fun instanceForDiffRow() = DiffUtilHelper<DiffRow>(
            id = DiffRow::id,
            hashCode = DiffRow::getHashToDiff
        )
    }

    constructor(
        id: ROW.() -> Any?,
        hashCode: ROW.() -> Int
    ): this(
        { oldItem, newItem -> id(oldItem) == id(newItem) },
        { oldItem, newItem -> hashCode(oldItem) == hashCode(newItem) }
    )

    override fun areItemsTheSame(
        oldItem: ROW,
        newItem: ROW
    ): Boolean = comparatorId(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: ROW,
        newItem: ROW
    ): Boolean = comparatorHash(oldItem, newItem)
}

interface DiffRow {

    val id: String
    fun getHashToDiff(): Int
}