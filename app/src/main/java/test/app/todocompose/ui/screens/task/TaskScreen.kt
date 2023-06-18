package test.app.todocompose.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(navigateToListScreen: (Action) -> Unit, selectedTask: ToDoTask?) {

    Scaffold(
        topBar = {
            TaskAppBar(navigateToListScreen = navigateToListScreen, selectedTask = selectedTask)
        },
        content = { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                TaskContent(
                    title = LoremIpsum(words = Constants.PREVIEW_LOREM_LARGE).values.joinToString(),
                    onTitleChange = {},
                    description = LoremIpsum(words = Constants.PREVIEW_LOREM_LARGE).values.joinToString(),
                    onPrioritySelected = {},
                    onDescriptionChange = {},
                    priority = Priority.HIGH
                )
            }
        })
}