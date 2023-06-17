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

@Composable
fun ListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (tasks.any()) {
        DisplayTasks(tasks, navigateToTaskScreen)
    } else {
        EmptyContent()
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

@Preview("Default", "Task")
@Preview("DarkMode", "Task", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TaskItemPreviewHigh() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "HIGH Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum",
            description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum  " +
                    "Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum  Lorem ipsum ",
            priority = Priority.HIGH
        ), navigateToTaskScreen = {})
}

@Preview("Default", "Task")
@Preview("DarkMode", "Task", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TaskItemPreviewLow() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "LOW",
            description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum ",
            priority = Priority.LOW
        ), navigateToTaskScreen = {})
}

@Preview("Default", "List")
@Preview("DarkMode", "List", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ListContentPreview() {
    ListContent(
        listOf(ToDoTask(0, "1", "2", Priority.HIGH), ToDoTask(0, "1", "2", Priority.HIGH)),
        {}
    )
}

@Preview("Default", "List")
@Preview("DarkMode", "List", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyListContentPreview() {
    ListContent(
        listOf(),
        {}
    )
}