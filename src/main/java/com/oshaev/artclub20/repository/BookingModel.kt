package com.oshaev.artclub20.repository

data class BookingModel(
    val id: String = "",
    var key: String = "",
    var isAccepted: Boolean? = false,
    val cabinet: String = "",
    var timestampStart: Long = 0,
    var timestampFinish: Long = 0,
    val uid: String = "",
    val members: Map<String,String> = emptyMap<String, String>(),
    val instruments: Map<String, String> = emptyMap<String, String>(),
    val comments: Map<String, CommentModel> = emptyMap<String, CommentModel>(),
    val userFio: String = ""
)