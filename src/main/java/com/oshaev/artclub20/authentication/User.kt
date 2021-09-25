package com.oshaev.artclub20.authentication

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
    )