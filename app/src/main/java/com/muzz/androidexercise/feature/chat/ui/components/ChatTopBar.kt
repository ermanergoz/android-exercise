package com.muzz.androidexercise.feature.chat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.muzz.androidexercise.R
import com.muzz.androidexercise.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Used TopAppBar to leverage Material 3 while still fully
    // controlling the layout to match the PDF design
    TopAppBar(modifier = modifier, navigationIcon = {
        // I used Box+clickable instead of IconButton to avoid its built in padding
        // to match the design on the pdf
        Box(
            modifier =
                Modifier
                    .size(Dimens.Icon.medium)
                    .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }, title = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing._2xs),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onProfileClick),
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.account),
                modifier = Modifier.size(Dimens.Icon.large),
            )
            Text(
                text = stringResource(R.string.sarah),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
            )
        }
    }, actions = {
        Box(
            modifier =
                Modifier
                    .size(Dimens.Icon.extraLarge)
                    .clickable(onClick = onMoreClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = stringResource(R.string.more),
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.fillMaxSize(),
            )
        }
    })
}

@Preview
@Composable
private fun ChatTopBarPreview() {
    ChatTopBar(
        onBackClick = {},
        onProfileClick = {},
        onMoreClick = {},
    )
}
