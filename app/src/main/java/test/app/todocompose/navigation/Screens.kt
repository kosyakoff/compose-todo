package test.app.todocompose.navigation

import androidx.navigation.NavHostController
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants.LIST_SCREEN
import test.app.todocompose.util.Constants.SPLASH_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

    val splash: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
}