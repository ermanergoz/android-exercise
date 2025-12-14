package com.muzz.androidexercise.feature.chat.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzz.androidexercise.feature.chat.data.repository.MessageRepository
import com.muzz.androidexercise.feature.chat.domain.model.Message
import com.muzz.androidexercise.feature.chat.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject
constructor(
    private val repository: MessageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        // Pre populate the chat so the chat won't be empty on first launch
        viewModelScope.launch {
            repository.seedIfEmpty()
        }
        // Then keep observing the latest messages from the DB
        observeMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.observeMessages().collect { messages ->
                _uiState.update { current ->
                    current.copy(
                        items = buildUiItems(messages),
                    )
                }
            }
        }
    }

    fun onAction(action: ChatAction) {
        when (action) {
            is ChatAction.OnInputChanged -> {
                _uiState.update { it.copy(input = action.text) }
            }

            ChatAction.OnSendClicked -> {
                sendCurrentMessage()
            }
            // The more action simply toggles the active user so
            // we can simulate a two-way conversation
            ChatAction.OnMoreClicked -> {
                toggleUser()
            }

            else -> {
                println(action)
            }
        }
    }

    private fun sendCurrentMessage() {
        val text = _uiState.value.input.trim()
        if (text.isEmpty()) return

        val from = _uiState.value.currentUser

        viewModelScope.launch {
            repository.sendMessage(text, from)
            _uiState.update { it.copy(input = "") }
        }
    }

    private fun toggleUser() {
        _uiState.update { state ->
            state.copy(
                currentUser =
                    if (state.currentUser == User.ME) {
                        User.SARAH
                    } else {
                        User.ME
                    },
            )
        }
    }

    // Builds the list of UI items from raw messages
    // inserts a time separator if the gap from the previous message is > 1 hour
    // groups messages from the same user if they are sent within 20 seconds
    private fun buildUiItems(messages: List<Message>): List<ChatItem> {
        if (messages.isEmpty()) return emptyList()

        val result = mutableListOf<ChatItem>()

        for (index in messages.indices) {
            val current = messages[index]
            val previous = messages.getOrNull(index - 1)
            val next = messages.getOrNull(index + 1)

            val currentMillis = current.timestampMillis
            val previousMillis = previous?.timestampMillis
            val nextMillis = next?.timestampMillis

            // Time separator
            val shouldInsertSeparator =
                previousMillis == null ||
                        currentMillis - previousMillis > ONE_HOUR_MILLIS

            if (shouldInsertSeparator) {
                result +=
                    ChatItem.TimeSeparator(
                        label = formatTimeLabel(currentMillis),
                    )
            }

            // Grouping
            val isFirstInGroup =
                previousMillis == null ||
                        previous.user != current.user ||
                        currentMillis - previousMillis >= TWENTY_SECONDS_MILLIS

            val compactSpacingBelow =
                nextMillis != null &&
                        next.user == current.user &&
                        (nextMillis - currentMillis) < TWENTY_SECONDS_MILLIS

            result +=
                ChatItem.MessageBubble(
                    id = current.id,
                    text = current.text,
                    user = current.user,
                    isFirstInGroup = isFirstInGroup,
                    compactSpacingBelow = compactSpacingBelow,
                )
        }
        return result
    }

    private fun formatTimeLabel(timestampMillis: Long): String {
        val date = Date(timestampMillis)
        val formatter = SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.getDefault())
        return formatter.format(date)
    }
}

private const val TIME_FORMAT_PATTERN = "EEEE HH:mm"
private const val ONE_HOUR_MILLIS = 60 * 60 * 1000L
private const val TWENTY_SECONDS_MILLIS = 20_000L
