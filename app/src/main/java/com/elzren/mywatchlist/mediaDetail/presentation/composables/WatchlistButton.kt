package com.elzren.mywatchlist.mediaDetail.presentation.composables

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun WatchlistButton(isInWatchlist: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (isInWatchlist) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.width(150.dp)
    ) {
        Text(
            text = if (isInWatchlist) "Remove" else "Add to Watchlist",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}