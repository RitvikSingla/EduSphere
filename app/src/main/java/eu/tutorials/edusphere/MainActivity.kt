package eu.tutorials.edusphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.edusphere.presentation.LoginScreen
import eu.tutorials.edusphere.presentation.RegisterScreen
import eu.tutorials.edusphere.presentation.SplashScreen
import eu.tutorials.edusphere.ui.theme.EduSphereTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EduSphereTheme {
                EduSphereAppNavigation(startDestination = "splash")
            }
        }
    }
}

@Composable
fun EduSphereAppNavigation(startDestination: String = "splash") {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash screen at top level
        composable("splash") {
            SplashScreen {
                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        // Auth flow with login and register
        navigation(startDestination = "login", route = "auth") {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate("register")
                    }
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }
        }

        // Home screen placeholder
        composable("home") {
            // HomeScreen() — implement later
        }
    }
}
