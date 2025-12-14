package com.muzz.androidexercise.feature.chat.presentation

sealed interface ChatAction {
    data class OnInputChanged(val text: String) : ChatAction

    data object OnSendClicked : ChatAction

    data object OnProfileClicked : ChatAction

    data object OnBackClicked : ChatAction

    data object OnMoreClicked : ChatAction
}
