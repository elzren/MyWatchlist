package com.example.playground.mediaDetail.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    infoList: List<String>,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = modifier
    ) {

        infoList.forEachIndexed { index, info ->
            Text(
                text = info,
                color = color,
            )
            if (index != infoList.size - 1) Text(
                text = "•",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = color
            )
        }
    }
}