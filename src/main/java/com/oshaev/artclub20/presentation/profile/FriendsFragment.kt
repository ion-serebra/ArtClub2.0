package com.oshaev.artclub20.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oshaev.artclub20.MainActivity
import com.oshaev.artclub20.R
import com.oshaev.artclub20.application.ArtClubApplication

class FriendsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.screen_friends, container, false)
        Log.d("FriendsFragment", "onCreateView")

        recyclerView = root.findViewById(R.id.friends_recycler)
        ArtClubApplication.authRepository.getUserFriends(ArtClubApplication.user.key).subscribe {
            adapter = FriendsAdapter(it.map {
                with(it) {
                    Friend(
                        key,
                        id,
                        fio,
                        creativityDirection,
                        avatarUrl,
                    )
                }
            }.toMutableList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            FriendsAdapter.clickListener = {

                parentFragmentManager.beginTransaction()
                    .add(R.id.nav_host_fragment_activity_main, ProfileFragment(userKey = it))
                    .addToBackStack("null").commit()
            }
        }
        return root
    }

    override fun onDetach() {
        (activity as MainActivity).unhideBottomNav()
        super.onDetach()
    }
}