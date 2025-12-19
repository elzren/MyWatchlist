package com.elzren.mywatchlist.settings.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val nsfw by viewModel.nsfw.collectAsState()
    val context = LocalContext.current
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    ScaffoldWithTopAppBar(
        title = stringResource(R.string.settings),
        scrollBehavior = topAppBarScrollBehavior,
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
    )
    { padding ->
        Column(
            modifier = modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ThemeSetting(currentTheme = currentTheme, onThemeChange = viewModel::setTheme)
            SwitchSetting(
                title = stringResource(R.string.show_nsfw_content),
                value = nsfw,
                icon = R.drawable.eighteen_up_rating_24px,
                onValueChange = { viewModel.setNsfw(it) }
            )
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
private fun ThemeSetting(
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

@Composable
private fun SwitchSetting(
    title: String,
    value: Boolean,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    @DrawableRes icon: Int? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    iconPadding: PaddingValues = PaddingValues(16.dp),
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onValueChange(value.not())
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = title,
                    modifier = Modifier.padding(iconPadding),
                    tint = iconTint
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(iconPadding)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title)
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Switch(
            checked = value,
            onCheckedChange = {
                onValueChange(it)
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}