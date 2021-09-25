package ru.sovcomcard.halva.v1.util

import androidx.recyclerview.widget.*

abstract class ListAdapter<T, VH: RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>):
    RecyclerView.Adapter<VH>() {

    private val mHelper: AsyncListDiffer<T> by lazy {
        AsyncListDiffer(AdapterListUpdateCallback(this), AsyncDifferConfig.Builder(diffCallback).build())
    }

    fun submitList(list: List<T>?) {
        mHelper.submitList(list)
    }

    fun getItem(position: Int): T {
        return mHelper.currentList[position]
    }

    protected fun getCurrentList(): List<T> {
        return mHelper.currentList
    }

    fun removeAt(position: Int) {
        val currentList = ArrayList(getCurrentList())
        currentList.removeAt(position)
        submitList(currentList)
    }

    override fun getItemCount(): Int {
        return mHelper.currentList.size
    }
}
