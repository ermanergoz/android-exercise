package com.muzz.androidexercise.feature.chat.data.repository

import com.muzz.androidexercise.feature.chat.domain.model.Message
import com.muzz.androidexercise.feature.chat.domain.model.User
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun observeMessages(): Flow<List<Message>>

    suspend fun sendMessage(
        text: String,
        from: User,
    )

    suspend fun seedIfEmpty()
}
