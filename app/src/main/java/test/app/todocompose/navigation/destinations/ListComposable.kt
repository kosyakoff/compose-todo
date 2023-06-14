package test.app.todocompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import test.app.todocompose.ui.screens.list.ListScreen
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.Constants

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.LIST_SCREEN,
        arguments = listOf(navArgument(Constants.LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)
    }
}