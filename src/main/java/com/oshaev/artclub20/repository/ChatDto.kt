package com.oshaev.artclub20.repository

data class ChatDto(
    val id: String = "",
    val name: String = "",
    var key: String = "",
    val messages: Map<String, ChatMessage> = emptyMap(),
    var member1Key: String = "",
    var member2Key: String = "",
    var isDialog: Boolean = false,
    var member1Name: String = "",
    var member2Name: String = ""
)

data class ChatModel(
    val id: String = "",
    val name: String = "",
    var key: String = "",
    val messages: List<ChatMessage> = emptyList(),
    var member1Key: String = "",
    var member2Key: String = "",
    var isDialog: Boolean = false,
    var member1Name: String = "",
    var member2Name: String = ""
)

data class ChatMessage(
    var id: String = "",
    var key: String = "",
    var text: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var timestamp: Long = 0,
    var userKey: String = ""
)