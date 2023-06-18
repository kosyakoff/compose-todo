package test.app.todocompose.ui.screens.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.ui.theme.taskItemBackgroundColor
import test.app.todocompose.ui.theme.taskItemTextColor
import test.app.todocompose.util.Constants
import test.app.todocompose.util.RequestState

@Composable
fun ListContent(
    requestState: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    when (requestState) {
        is RequestState.Error -> {}
        RequestState.Idle -> {}
        RequestState.Loading -> {}
        is RequestState.Success -> {
            if (requestState.data.any()) {
                DisplayTasks(requestState.data, navigateToTaskScreen)
            } else {
                EmptyContent()
            }
        }
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.SMALL_PADDING),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = Dimensions.TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = Dimensions.LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(8f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(Dimensions.PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Text(
                text = toDoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.taskItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(modifier = Modifier, content = {
        items(items = tasks, itemContent = {
            TaskItem(toDoTask = it, navigateToTaskScreen = navigateToTaskScreen)
        })
    })
}

@Preview(Constants.PREVIEW_DEFAULT, "Task")
@Preview(Constants.PREVIEW_DARK_MODE, "Task", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreviewHigh() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "HIGH Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum",
            description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum  " +
                    "Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum ",
            priority = Priority.HIGH
        ), navigateToTaskScreen = {})
}

@Preview(Constants.PREVIEW_DEFAULT, "Task")
@Preview(Constants.PREVIEW_DARK_MODE, "Task", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreviewLow() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "LOW",
            description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
            priority = Priority.LOW
        ), navigateToTaskScreen = {})
}

@Preview(Constants.PREVIEW_DEFAULT, "List")
@Preview(Constants.PREVIEW_DARK_MODE, "List", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ListContentPreview() {
    ListContent(
        RequestState.Success(
            listOf(
                ToDoTask(0, "1", "2", Priority.HIGH),
                ToDoTask(0, "1", "2", Priority.HIGH)
            )
        ),
        {}
    )
}

@Preview(Constants.PREVIEW_DEFAULT, "List")
@Preview(Constants.PREVIEW_DARK_MODE, "List", uiMode = UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x050505)
@Composable
private fun EmptyListContentPreview() {
    ListContent(
        RequestState.Success(listOf()),
        {}
    )
}