package test.app.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import test.app.todocompose.navigation.destinations.listComposable
import test.app.todocompose.navigation.destinations.splashComposable
import test.app.todocompose.navigation.destinations.taskComposable
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(navHostController: NavHostController, sharedViewModel: SharedViewModel) {
    val screen = remember(navHostController) {
        Screens(navController = navHostController)
    }

    NavHost(navController = navHostController, startDestination = SPLASH_SCREEN) {
        splashComposable(navigateToListScreen = screen.splash)
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
    }
}