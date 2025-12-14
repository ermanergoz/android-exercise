package com.muzz.androidexercise.feature.chat.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muzz.androidexercise.feature.chat.domain.model.User
import com.muzz.androidexercise.feature.chat.presentation.ChatAction
import com.muzz.androidexercise.feature.chat.presentation.ChatItem
import com.muzz.androidexercise.feature.chat.presentation.ChatUiState
import com.muzz.androidexercise.feature.chat.presentation.ChatViewModel
import com.muzz.androidexercise.feature.chat.ui.components.ChatTopBar
import com.muzz.androidexercise.feature.chat.ui.components.MessageInputBar
import com.muzz.androidexercise.feature.chat.ui.components.MessageList
import com.muzz.androidexercise.ui.theme.AndroidExerciseTheme

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ChatScreenContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
private fun ChatScreenContent(
    state: ChatUiState,
    onAction: (ChatAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ChatTopBar(
                onBackClick = { onAction(ChatAction.OnBackClicked) },
                onProfileClick = { onAction(ChatAction.OnProfileClicked) },
                onMoreClick = { onAction(ChatAction.OnMoreClicked) },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            MessageList(
                items = state.items,
                modifier = Modifier.weight(1f),
            )

            MessageInputBar(
                text = state.input,
                onTextChange = { onAction(ChatAction.OnInputChanged(it)) },
                onSendClicked = { onAction(ChatAction.OnSendClicked) },
            )
        }
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    val previewState =
        ChatUiState(
            input = "Typing a message",
            items =
                listOf(
                    ChatItem.TimeSeparator("Thursday 11:59"),
                    ChatItem.MessageBubble(
                        id = 1,
                        text = "Hey Sarah, how are you?",
                        user = User.ME,
                        isFirstInGroup = true,
                        compactSpacingBelow = true,
                    ),
                    ChatItem.MessageBubble(
                        id = 2,
                        text = "Did you see the new shoes I got?",
                        user = User.ME,
                        isFirstInGroup = false,
                        compactSpacingBelow = false,
                    ),
                    ChatItem.TimeSeparator("Thursday 12:05"),
                    ChatItem.MessageBubble(
                        id = 3,
                        text = "Hey! Yes, looks great 😊",
                        user = User.SARAH,
                        isFirstInGroup = true,
                        compactSpacingBelow = false,
                    ),
                ),
        )

    AndroidExerciseTheme {
        ChatScreenContent(
            state = previewState,
            onAction = {},
        )
    }
}
