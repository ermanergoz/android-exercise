package com.muzz.androidexercise.feature.chat.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.muzz.androidexercise.ui.theme.Dimens

@Composable
fun TimeSeparator(
    text: String,
    modifier: Modifier = Modifier,
) {
    val parts = text.split(SEPARATOR_DELIMITER, limit = SEPARATOR_LIMIT)
    val time = parts.getOrNull(DATE_INDEX).orEmpty()

    Box(
        modifier = modifier.padding(vertical = Dimens.Spacing._2xs),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            Text(
                text = parts.getOrNull(TIME_INDEX).orEmpty(),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
                    .copy(alpha = Dimens.Opacity.default),
            )
            Text(
                text = "$TIME_PREFIX$time",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
                    .copy(alpha = Dimens.Opacity.default),
            )
        }
    }
}

private const val SEPARATOR_DELIMITER = " "
private const val SEPARATOR_LIMIT = 2
private const val DATE_INDEX = 1
private const val TIME_INDEX = 0
private const val TIME_PREFIX = " "

@Preview
@Composable
private fun TimeSeparatorPreview() {
    TimeSeparator(text = "Thursday 11:59")
}
