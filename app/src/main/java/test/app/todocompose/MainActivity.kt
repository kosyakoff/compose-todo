package test.app.todocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import test.app.todocompose.navigation.SetupNavigation
import test.app.todocompose.ui.theme.ToDoComposeTheme
import test.app.todocompose.ui.viewModels.SharedViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ToDoComposeTheme {
                navController = rememberNavController()
                SetupNavigation(navHostController = navController, sharedViewModel)
            }
        }
    }
}
