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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodwasteapplication.food.FoodViewModel

private enum class BottomDestination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
) {
    HOME("home", "Home", Icons.Filled.Home),
    ADD_FOOD("add_food", "Food List", Icons.Filled.AddCircle),
    SHARE("share", "Share", Icons.Filled.Share),
    STATS("stats", "Statistics", Icons.Filled.BarChart),
}

private const val PROFILE_ROUTE = "profile"

@Composable
fun MainScreen(
    onLogoutClick: () -> Unit,
) {
    val navController = rememberNavController()
    val foodViewModel: FoodViewModel = viewModel()
    val foods by foodViewModel.foods.collectAsState()
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
                    },
                    onNavigateToRoute = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(BottomDestination.ADD_FOOD.route) {
                FoodListScreen(
                    topPadding = innerPadding,
                    foods = foods,
                    onAddFood = {
                        navController.navigate(buildFoodEditorRoute())
                    },
                    onEditFood = { foodId ->
                        navController.navigate(buildFoodEditorRoute(foodId))
                    },
                    onDeleteFood = { food ->
                        foodViewModel.deleteFood(food)
                    }
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
                    title = "Statistics",
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
            composable(
                route = FOOD_EDITOR_ROUTE,
                arguments = listOf(
                    navArgument(FOOD_EDITOR_ARG_ID) {
                        type = NavType.LongType
                    }
                )
            ) { navBackStackEntry ->
                val foodId = navBackStackEntry
                    .arguments
                    ?.getLong(FOOD_EDITOR_ARG_ID, NEW_FOOD_ID) ?: NEW_FOOD_ID

                FoodEditorScreen(
                    topPadding = innerPadding,
                    foodId = foodId,
                    viewModel = foodViewModel,
                    onSavedOrClosed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
