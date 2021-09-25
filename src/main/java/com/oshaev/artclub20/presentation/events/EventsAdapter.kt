package com.oshaev.artclub20.presentation.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.oshaev.artclub20.R
import com.oshaev.artclub20.repository.events.EventData
import kotlinx.android.synthetic.main.item_event_card.view.*
import java.util.*

class EventsAdapter:
    CardSliderAdapter<EventsAdapter.EventViewHolder>() {

    lateinit var eventsList: List<EventData>

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    fun submitList(list:List<EventData>){
        eventsList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card, parent, false)
        return EventViewHolder(root)
    }

    override fun bindVH(holder: EventViewHolder, position: Int) {

        holder.itemView.run {
            Glide.with(postImagePreview.context)
                .load(eventsList[position].imageUrlString)
                .into(postImagePreview)
        }
    }
}
















