package com.example.foodwasteapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private enum class BottomDestination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
) {
    HOME("home", "Home", Icons.Filled.Home),
    ADD_FOOD("add_food", "Add Food", Icons.Filled.AddCircle),
    SHARE("share", "Share", Icons.Filled.Share),
    STATS("stats", "Stats", Icons.Filled.BarChart),
}

private const val PROFILE_ROUTE = "profile"

@Composable
fun MainScreen(
    onLogoutClick: () -> Unit,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = BottomDestination.entries.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    BottomDestination.entries.forEach { destination ->
                        NavigationBarItem(
                            selected = currentRoute == destination.route,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.label
                                )
                            },
                            label = { Text(destination.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomDestination.HOME.route,
            modifier = Modifier
        ) {
            composable(BottomDestination.HOME.route) {
                HomeEntryScreen(
                    topPadding = innerPadding,
                    onOpenProfile = {
                        navController.navigate(PROFILE_ROUTE)
                    }
                )
            }
            composable(BottomDestination.ADD_FOOD.route) {
                ModulePlaceholderScreen(
                    topPadding = innerPadding,
                    title = "Add Food",
                    owner = "Member 2",
                    description = "CRUD form and Room database flow will be connected here."
                )
            }
            composable(BottomDestination.SHARE.route) {
                ModulePlaceholderScreen(
                    topPadding = innerPadding,
                    title = "Share",
                    owner = "Member 4",
                    description = "Firebase food sharing and community listings will be added here."
                )
            }
            composable(BottomDestination.STATS.route) {
                ModulePlaceholderScreen(
                    topPadding = innerPadding,
                    title = "Stats",
                    owner = "Member 4",
                    description = "Waste analytics and charts will be implemented in this screen."
                )
            }
            composable(PROFILE_ROUTE) {
                ProfileScreen(
                    onBackHome = {
                        navController.popBackStack()
                    },
                    onNavigateToRoute = { route ->
                        if (route == PROFILE_ROUTE) return@ProfileScreen
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogoutClick = onLogoutClick
                )
            }
        }
    }
}
