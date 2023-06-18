package test.app.todocompose.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current

    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority


    Scaffold(
        topBar = {
            TaskAppBar(navigateToListScreen = { action ->
                if (action == Action.NO_ACTION || sharedViewModel.validateFields()) {
                    navigateToListScreen(action)
                } else {
                    displayToast(context = context)
                }
            }, selectedTask = selectedTask)
        },
        content = { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                TaskContent(
                    title = title,
                    onTitleChange = {
                        sharedViewModel.updateTitle(it)
                    },
                    description = description,
                    onPrioritySelected = {
                        sharedViewModel.priority.value = it
                    },
                    onDescriptionChange = {
                        sharedViewModel.description.value = it
                    },
                    priority = priority
                )
            }
        })
}

private fun displayToast(context: Context) {
    Toast.makeText(context, "Fields are empty", Toast.LENGTH_LONG).show()
}