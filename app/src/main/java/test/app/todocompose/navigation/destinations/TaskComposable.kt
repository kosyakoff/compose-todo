package test.app.todocompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import test.app.todocompose.ui.screens.task.TaskScreen
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = Constants.TASK_SCREEN,
        arguments = listOf(navArgument(Constants.TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(Constants.TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            sharedViewModel.updateTaskFields(selectedTask)
        }

        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel
        )
    }
}