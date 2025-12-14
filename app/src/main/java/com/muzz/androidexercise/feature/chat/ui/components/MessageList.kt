package com.muzz.androidexercise.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.muzz.androidexercise.feature.chat.presentation.ChatItem
import com.muzz.androidexercise.ui.theme.Dimens

@Composable
fun MessageList(
    items: List<ChatItem>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    // Scroll to bottom when new messages arrive
    LaunchedEffect(items.size) {
        if (items.isNotEmpty()) {
            listState.animateScrollToItem(items.lastIndex)
        }
    }

    Box(modifier = modifier) {
        MessageListContent(
            items = items,
            listState = listState,
        )
        MessageListShadows()
    }
}

@Composable
private fun MessageListContent(
    items: List<ChatItem>,
    listState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.Spacing.s,
                    end = Dimens.Spacing.s,
                ),
        state = listState,
    ) {
        itemsIndexed(
            items = items,
            key = { index, item ->
                when (item) {
                    is ChatItem.MessageBubble -> "$MESSAGE_KEY_PREFIX${item.id}"
                    is ChatItem.TimeSeparator -> "$SEPARATOR_KEY_PREFIX${item.label}_$index"
                }
            },
        ) { _, item ->
            when (item) {
                is ChatItem.TimeSeparator -> {
                    TimeSeparator(
                        text = item.label,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                is ChatItem.MessageBubble -> {
                    MessageBubble(item = item)
                    // Messages should have a small message separation spacing if
                    // the message after it is sent by the same user
                    // the message after it was sent less than 20 seconds afterwards
                    val height =
                        if (item.compactSpacingBelow) {
                            Dimens.Spacing._2xs
                        } else {
                            Dimens.Spacing.s
                        }
                    Spacer(modifier = Modifier.height(height))
                }
            }
        }
    }
}

@Composable
private fun MessageListShadows(modifier: Modifier = Modifier) {
    val edgeShadowColor =
        MaterialTheme.colorScheme.onSurfaceVariant.copy(
            alpha = Dimens.Opacity.edgeShadow,
        )

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.Spacing._2xs)
                    .align(Alignment.TopCenter)
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        edgeShadowColor,
                                        Color.Transparent,
                                    ),
                            ),
                    ),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(Dimens.Spacing._2xs)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Transparent,
                                        edgeShadowColor,
                                    ),
                            ),
                    ),
        )
    }
}

private const val MESSAGE_KEY_PREFIX = "msg_"
private const val SEPARATOR_KEY_PREFIX = "sep_"
