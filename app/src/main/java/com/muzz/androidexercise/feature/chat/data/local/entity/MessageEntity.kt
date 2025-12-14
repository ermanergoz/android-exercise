package com.muzz.androidexercise.feature.chat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val text: String,
    val user: String,
    val timestampMillis: Long,
)
