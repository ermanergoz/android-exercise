package com.muzz.androidexercise.feature.chat.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.muzz.androidexercise.R
import com.muzz.androidexercise.ui.theme.Dimens

// I would use OutlinedTextField to simplify the implementation but
// the inner paddings didn't match the design that is on the pdf
@Composable
fun MessageInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    start = Dimens.Spacing.s,
                    end = Dimens.Spacing.s,
                    top = Dimens.Spacing._2xs,
                    bottom = Dimens.Spacing._2xs,
                ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.s),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MessageTextField(
            text = text,
            onTextChange = onTextChange,
            onSendClicked = {
                onSendClicked()
                keyboardController?.hide()
            },
            modifier = Modifier.weight(1f),
        )

        SendMessageButton(
            enabled = text.isNotBlank(),
            onSendClicked = {
                onSendClicked()
                keyboardController?.hide()
            },
        )
    }
}

@Composable
private fun MessageTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor =
        if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline
        }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        textStyle =
            MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(onSend = { onSendClicked() }),
        interactionSource = interactionSource,
        modifier =
            modifier
                .clip(MESSAGE_INPUT_SHAPE)
                .border(
                    BorderStroke(Dimens.Border.default, borderColor),
                    shape = MESSAGE_INPUT_SHAPE,
                )
                .padding(
                    horizontal = Dimens.Spacing.s,
                    vertical = Dimens.Spacing._2xs,
                )
                .height(Dimens.Spacing.l),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
            ) {
                innerTextField()
            }
        },
    )
}

@Composable
private fun SendMessageButton(
    enabled: Boolean,
    onSendClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onSendClicked,
        enabled = enabled,
        shape = CircleShape,
        colors =
            IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor =
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = Dimens.Opacity.disabled,
                    ),
            ),
        modifier = modifier.size(Dimens.Button.extraLarge),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.Send,
            contentDescription = stringResource(R.string.content_description_send),
            tint = Color.White,
        )
    }
}

// To avoid recreation on every recomposition
private val MESSAGE_INPUT_SHAPE = RoundedCornerShape(Dimens.Radius.pill)

@Preview
@Composable
private fun MessageInputBarFocusedPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.s),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MessageTextField(
            text = "Focused input",
            onTextChange = {},
            onSendClicked = {},
            modifier = Modifier.weight(1f),
        )
        SendMessageButton(enabled = true, onSendClicked = {})
    }
}
