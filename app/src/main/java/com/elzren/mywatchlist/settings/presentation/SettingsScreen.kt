package com.elzren.mywatchlist.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.elzren.mywatchlist.BuildConfig
import com.elzren.mywatchlist.R
import com.elzren.mywatchlist.core.domain.model.Theme
import com.elzren.mywatchlist.core.presentation.composables.IconTextRow
import com.elzren.mywatchlist.core.presentation.composables.ScaffoldWithTopAppBar
import com.elzren.mywatchlist.core.presentation.navigation.NavActionManager
import com.elzren.mywatchlist.core.utils.Constants
import com.elzren.mywatchlist.core.utils.ContextUtils.copyToClipboard
import com.elzren.mywatchlist.core.utils.ContextUtils.openActionView

private const val versionString = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navActionManager: NavActionManager,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val currentTheme by viewModel.theme.collectAsState()
    val context = LocalContext.current
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    ScaffoldWithTopAppBar(
        title = stringResource(R.string.settings),
        scrollBehavior = topAppBarScrollBehavior,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ThemeSetting(currentTheme = currentTheme, onThemeChange = viewModel::setTheme)
            IconTextRow(
                title = stringResource(R.string.github_repository),
                icon = R.drawable.github_24px,
                onClick = {
                    context.openActionView(Constants.GITHUB_REPO_URL.toUri())
                }
            )
            IconTextRow(
                title = stringResource(R.string.version),
                subtitle = versionString,
                icon = R.drawable.code_24px,
                onClick = {
                    context.copyToClipboard(versionString)
                }
            )
        }
    }
}

@Composable
fun ThemeSetting(
    currentTheme: Theme,
    onThemeChange: (theme: Theme) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDialogOpen by rememberSaveable { mutableStateOf(false) }

    IconTextRow(
        title = stringResource(R.string.theme),
        subtitle = stringResource(currentTheme.string),
        icon = R.drawable.palette_24px,
        onClick = {
            isDialogOpen = true
        },
        modifier = modifier
    )

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            confirmButton = {
                TextButton(onClick = { isDialogOpen = false }) {
                    Text(text = stringResource(R.string.close))
                }
            },
            title = { Text(text = stringResource(R.string.theme)) },
            text = {
                LazyColumn {
                    items(items = Theme.entries) { theme ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { onThemeChange(theme) })
                                .padding(vertical = 16.dp)
                        ) {
                            RadioButton(
                                selected = currentTheme == theme,
                                onClick = null,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            Text(text = stringResource(theme.string))
                        }
                    }
                }
            }
        )
    }
}