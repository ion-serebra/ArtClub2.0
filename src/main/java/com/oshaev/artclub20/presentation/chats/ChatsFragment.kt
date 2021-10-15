package com.oshaev.artclub20.presentation.chats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.MainActivity
import com.oshaev.artclub20.R
import com.oshaev.artclub20.presentation.viewmodels.ChatsViewModel

class ChatsFragment: Fragment() {

    companion object {

        fun newInstance() = ChatsFragment()
    }

    private lateinit var viewModel: ChatsViewModel
    private var adapter: ChatsAdapter = ChatsAdapter()
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)

        var rootView = inflater.inflate(R.layout.screen_chats, container, false)
        Log.d("CurrentFragment", "onCreateView ChatsFragment")
        var manager = LinearLayoutManager(this.context)
        recyclerView = rootView.findViewById(R.id.chats_recycler)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        viewModel.getChatsList().subscribe { list ->
            adapter.submitList(list)
            Log.d("ChatsFragment", list.toString())

            adapter.chatClickListener
        }

        adapter.chatClickListener = {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("chatId", it.id)
            startActivity(intent)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        Log.d("CurrentFragment", "onResume ChatsFragment")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CurrentFragment", "onStop ChatsFragment")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}