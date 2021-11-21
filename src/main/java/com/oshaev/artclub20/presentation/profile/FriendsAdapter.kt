package com.oshaev.artclub20.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oshaev.artclub20.R
import kotlinx.android.synthetic.main.view_friend_item.view.*

class FriendsAdapter(var friends: MutableList<Friend>): RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    companion object {
        var clickListener: ((friendId: String) -> Unit)? = null
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_friend_item, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(friends[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return friends.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var clickListener = 9

        fun bindItems(friend: Friend) {
            itemView.friend_item_name.text = friend.name
            itemView.friend_item_status.text = friend.status
            Glide.with(itemView).load(friend.avatarUrl).into(itemView.friend_item_avatar)

            itemView.setOnClickListener {
                FriendsAdapter.clickListener?.invoke(friend.key)
            }
        }
    }
}