package com.muzz.androidexercise.feature.chat.presentation

import com.muzz.androidexercise.feature.chat.domain.model.User

sealed interface ChatItem {
    data class TimeSeparator(
        val label: String,
    ) : ChatItem

    data class MessageBubble(
        val id: Long,
        val text: String,
        val user: User,
        val isFirstInGroup: Boolean,
        val compactSpacingBelow: Boolean,
    ) : ChatItem
}

data class ChatUiState(
    val items: List<ChatItem> = emptyList(),
    val input: String = "",
    val currentUser: User = User.ME,
)
