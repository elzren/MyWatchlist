package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elzren.mywatchlist.mediaDetail.domain.model.keyword.Keyword

@Composable
fun Keywords(keywords: List<Keyword>, modifier: Modifier = Modifier) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        keywords.forEach { keyword ->
            ElevatedAssistChip(
                onClick = {},
                label = { Text(text = keyword.name) }
            )
        }
    }
}