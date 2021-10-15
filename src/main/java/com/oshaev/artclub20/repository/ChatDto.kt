package com.oshaev.artclub20.repository

data class ChatDto(
    val id: String = "",
    val name: String = "",
    var key: String = "",
    val messages: Map<String, ChatMessage> = emptyMap(),
)

data class ChatModel(
    val id: String = "",
    val name: String = "",
    var key: String = "",
    val messages: List<ChatMessage> = emptyList(),
)

data class ChatMessage(
    var id: String = "",
    var key: String = "",
    var text: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var timestamp: Long = 0,
)