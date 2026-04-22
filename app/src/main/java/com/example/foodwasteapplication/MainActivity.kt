package com.example.foodwasteapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodwasteapplication.auth.MockAuthStore
import com.example.foodwasteapplication.navigation.AppRoute
import com.example.foodwasteapplication.ui.screens.LoginScreen
import com.example.foodwasteapplication.ui.screens.MainScreen
import com.example.foodwasteapplication.ui.screens.RegisterScreen
import com.example.foodwasteapplication.ui.theme.FoodwasteApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodwasteApplicationTheme {
                FoodwasteApplicationApp()
            }
        }
    }
}

@Composable
fun FoodwasteApplicationApp() {
    val navController = rememberNavController()
    var lastRegisteredEmail by rememberSaveable { mutableStateOf("") }
    var registrationNotice by rememberSaveable { mutableStateOf("") }

    NavHost(
        navController = navController,
        startDestination = AppRoute.Login.route
    ) {
        composable(AppRoute.Login.route) {
            LoginScreen(
                prefilledEmail = lastRegisteredEmail,
                loginNotice = registrationNotice,
                onLoginSuccess = {
                    lastRegisteredEmail = ""
                    registrationNotice = ""
                    navController.navigate(AppRoute.Main.route) {
                        popUpTo(AppRoute.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    registrationNotice = ""
                    navController.navigate(AppRoute.Register.route)
                }
            )
        }
        composable(AppRoute.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { registeredEmail ->
                    lastRegisteredEmail = registeredEmail
                    registrationNotice = "Account created. Please log in with your new password."
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable(AppRoute.Main.route) {
            MainScreen(
                onLogoutClick = {
                    MockAuthStore.logout()
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodwasteApplicationAppPreview() {
    FoodwasteApplicationTheme {
        FoodwasteApplicationApp()
    }
}
