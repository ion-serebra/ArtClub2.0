package com.oshaev.artclub20.presentation.chats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oshaev.artclub20.R
import com.oshaev.artclub20.presentation.viewmodels.ChatsViewModel

class ChatsFragment: Fragment() {

    companion object {

        fun newInstance() = ChatsFragment()
    }

    private lateinit var viewModel: ChatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.screen_chats, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatsViewModel::class.java)
        // TODO: Use the ViewModel
    }
}