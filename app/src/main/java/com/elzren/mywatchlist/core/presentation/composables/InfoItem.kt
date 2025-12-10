package com.elzren.mywatchlist.core.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.R

@Composable
fun InfoItem(
    title: String,
    info: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .then(modifier)
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SelectionContainer(
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = info ?: stringResource(R.string.unknown),
                maxLines = 2,
            )
        }
    }
}