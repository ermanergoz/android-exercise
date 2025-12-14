package com.muzz.androidexercise.feature.chat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.muzz.androidexercise.R
import com.muzz.androidexercise.feature.chat.domain.model.User
import com.muzz.androidexercise.feature.chat.presentation.ChatItem
import com.muzz.androidexercise.ui.theme.Dimens

@Composable
fun MessageBubble(
    item: ChatItem.MessageBubble,
    modifier: Modifier = Modifier,
) {
    val isMe = item.user == User.ME

    val bubbleColor =
        if (isMe) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val textColor =
        if (isMe) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    MessageBubbleContent(
        text = item.text,
        isMe = isMe,
        bubbleColor = bubbleColor,
        textColor = textColor,
        modifier = modifier,
    )
}

@Composable
private fun MessageBubbleContent(
    text: String,
    isMe: Boolean,
    bubbleColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.Spacing._2xs),
        horizontalArrangement =
            if (isMe) {
                Arrangement.End
            } else {
                Arrangement.Start
            },
    ) {
        // BoxWithConstraints lets the bubble size to its content for short texts,
        // and cap its width to 80% of the available space for long texts
        BoxWithConstraints {
            Box(
                Modifier
                    .widthIn(max = maxWidth * MESSAGE_BUBBLE_MAX_WIDTH_FRACTION)
                    .background(
                        color = bubbleColor,
                        shape = bubbleShape(isMe = isMe),
                    )
                    .padding(Dimens.Spacing._3xs),
            ) {
                Text(
                    text = text,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(
                                end = Dimens.Spacing.xs,
                                start = Dimens.Spacing._2xs,
                                top = Dimens.Spacing._3xs,
                                bottom = Dimens.Spacing._3xs,
                            ),
                )

                if (isMe) {
                    ReadReceiptIcon(
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .size(Dimens.Icon.small),
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadReceiptIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.DoneAll,
        contentDescription = stringResource(R.string.read_receipt),
        tint = MaterialTheme.colorScheme.outline,
        modifier = modifier,
    )
}

private fun bubbleShape(isMe: Boolean): RoundedCornerShape {
    val radius = Dimens.Radius.s

    return if (isMe) {
        RoundedCornerShape(
            topStart = radius,
            topEnd = radius,
            bottomStart = radius,
        )
    } else {
        RoundedCornerShape(
            topStart = radius,
            topEnd = radius,
            bottomEnd = radius,
        )
    }
}

private const val MESSAGE_BUBBLE_MAX_WIDTH_FRACTION = 0.8f

@Preview
@Composable
private fun MessageBubblePreview() {
    MessageBubble(
        item =
            ChatItem.MessageBubble(
                id = 1,
                text = "This is a reply from Sarah. This message is a bit longer to show how it wraps.",
                user = User.SARAH,
                isFirstInGroup = false,
                compactSpacingBelow = true,
            ),
    )
}
