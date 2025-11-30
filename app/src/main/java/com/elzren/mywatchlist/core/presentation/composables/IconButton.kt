package com.elzren.mywatchlist.core.presentation.composables

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.elzren.mywatchlist.R

@Composable
fun BackIconButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.arrow_back_24px),
            contentDescription = stringResource(R.string.back)
        )
    }
}

@Composable
fun CloseIconButton(onClick: () -> Unit, @StringRes description: Int = R.string.close) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.close_24px),
            contentDescription = stringResource(description)
        )
    }
}