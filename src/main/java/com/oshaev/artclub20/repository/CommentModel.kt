package com.oshaev.artclub20.repository

data class CommentModel(
    val id: String = "",
    val bookingKey: String = "",
    val text: String = "",
    val uid: String = "",
    val timestamp: Long = 0
)