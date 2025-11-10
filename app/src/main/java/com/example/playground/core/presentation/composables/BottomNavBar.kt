package com.example.playground.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.playground.core.presentation.BottomDestination
import com.example.playground.core.presentation.navigation.Routes


@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isBottomDestination =
        BottomDestination.entries.any { destination -> currentDestination?.hasRoute(destination.route::class) == true }

    AnimatedVisibility(
        visible = isBottomDestination,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            BottomDestination.entries.forEach { destination ->
                val isSelected = currentDestination?.hasRoute(destination.route::class) == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(
                            destination.route,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(
                                    route = Routes.Home,
                                    inclusive = false,
                                    saveState = true    // save state for later restoration (preserves ui state (like scroll position or form input)
                                )
                                .setRestoreState(true)  // restore any state previously saved by PopUpToBuilder.saveState
                                .setLaunchSingleTop(true)   // wont launch a new instance if destination is already at the top of stack
                                .build()
                        )
                    },
                    label = { Text(text = stringResource(destination.label)) },
                    icon = {
                        Icon(
                            painter = painterResource(if (isSelected) destination.iconSelected else destination.icon),
                            contentDescription = stringResource(destination.label)
                        )
                    }
                )
            }
        }
    }
}