package com.oshaev.artclub20.authentication

import com.oshaev.artclub20.presentation.profile.Friend

data class User(
    val id: String = "",
    val fio: String = "",
    val surname: String = "",
    val avatarUrl: String = "",
    val email: String = "",
    val userDescription: String = "",
    val group: String = "",
    val phone: String = "",
    val vkLink: String = "",
    val instLink: String = "",
    val birthday: String = "",
    val lastLoginDate: Long = 0,
    val registrationDate: Long = 0,
    val clothingSize: String = "",
    val creativityDirection: String = "",
    val accessLevel: Int = 0,
    var key: String = "",
    var exp: Int = 0,
    var level: Int = 0,
    var friends: MutableList<String> = mutableListOf(), // список ключей друзей
    var tasks: List<String> = listOf(),
    var events: List<String> = listOf()
    )