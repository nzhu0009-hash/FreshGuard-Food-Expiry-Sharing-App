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

@Composable
fun MainScreen(
    onLogoutClick: () -> Unit,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomDestination.HOME.route,
            modifier = Modifier
        ) {
            composable(BottomDestination.HOME.route) {
                PlaceholderScreen(
                    title = "Home",
                    subtitle = "Food list screen placeholder for Member 2.",
                    accent = "Upcoming Expiry",
                    topPadding = innerPadding
                )
            }
            composable(BottomDestination.ADD_FOOD.route) {
                PlaceholderScreen(
                    title = "Add Food",
                    subtitle = "Add or edit food item placeholder.",
                    accent = "Room + DatePicker",
                    topPadding = innerPadding
                )
            }
            composable(BottomDestination.SHARE.route) {
                PlaceholderScreen(
                    title = "Share",
                    subtitle = "Food sharing feature placeholder for Member 4.",
                    accent = "Firebase Sharing",
                    topPadding = innerPadding
                )
            }
            composable(BottomDestination.STATS.route) {
                PlaceholderScreen(
                    title = "Stats",
                    subtitle = "Waste statistics and charts placeholder.",
                    accent = "Visual Analytics",
                    topPadding = innerPadding,
                    actionText = "Log out",
                    onActionClick = onLogoutClick
                )
            }
        }
    }
}
