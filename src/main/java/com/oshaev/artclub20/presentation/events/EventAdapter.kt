package com.oshaev.artclub20.presentation.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.oshaev.artclub20.R
import com.oshaev.artclub20.repository.events.EventData
import kotlinx.android.synthetic.main.item_event_card.view.*
import kotlinx.android.synthetic.main.item_event_card.view.postImagePreview
import kotlinx.android.synthetic.main.view_event_category.view.*

class EventAdapter:
    CardSliderAdapter<EventAdapter.EventViewHolder>() {

    var eventsList: List<EventData> = listOf()

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    fun submitList(list:List<EventData>){
        eventsList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_event_category, parent, false)
        return EventViewHolder(root)
    }

    override fun bindVH(holder: EventViewHolder, position: Int) {

        holder.itemView.run {
            Glide.with(event_category_image.context)
                .load(eventsList[position].imageUrlString)
                .into(event_category_image)
            event_category_text.text = eventsList[position].title
        }



    }
}