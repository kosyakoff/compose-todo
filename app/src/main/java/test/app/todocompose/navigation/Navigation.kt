package test.app.todocompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import test.app.todocompose.navigation.destinations.listComposable
import test.app.todocompose.navigation.destinations.taskComposable
import test.app.todocompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(navHostController: NavHostController) {
    val screen = remember(navHostController) {
        Screens(navController = navHostController)
    }

    NavHost(navController = navHostController, startDestination = LIST_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}