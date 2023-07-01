package test.app.todocompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import test.app.todocompose.ui.screens.list.ListScreen
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit, sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.LIST_SCREEN, arguments = listOf(navArgument(Constants.LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(Constants.LIST_ARGUMENT_KEY)
            ?.let { Action.valueOf(it) } ?: Action.NO_ACTION

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }

        val databaseAction by sharedViewModel.action

        ListScreen(
            action = databaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}