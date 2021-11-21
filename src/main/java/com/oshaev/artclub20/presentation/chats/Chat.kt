package com.oshaev.artclub20.presentation.chats

import com.oshaev.artclub20.authentication.User
import java.util.ArrayList

class Chat {

     var name: String? = null
     var DBChild: String? = null
     var id: String? = null
     var accessLevel = 0
     var chatMembers: List<User> = ArrayList<User>()
     var member1Key: String = ""
     var member2Key: String = ""
}