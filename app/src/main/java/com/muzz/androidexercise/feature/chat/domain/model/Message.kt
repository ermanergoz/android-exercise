package com.muzz.androidexercise.feature.chat.domain.model

data class Message(
    val id: Long,
    val text: String,
    val user: User,
    val timestampMillis: Long,
)
