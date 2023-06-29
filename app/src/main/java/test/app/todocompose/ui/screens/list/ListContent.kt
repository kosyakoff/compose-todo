package test.app.todocompose.ui.screens.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.ui.theme.HighPriorityColor
import test.app.todocompose.ui.theme.taskItemBackgroundColor
import test.app.todocompose.ui.theme.taskItemTextColor
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants
import test.app.todocompose.util.RequestState
import test.app.todocompose.util.SearchAppBarState

@Composable
fun ListContent(
    allTasksRequestState: RequestState<List<ToDoTask>>,
    searchedTasksRequestState: RequestState<List<ToDoTask>>,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {

    if (sortState !is RequestState.Success) {
        return
    }

    when {
        searchAppBarState == SearchAppBarState.TRIGGERED -> {
            if (searchedTasksRequestState is RequestState.Success) {
                HandleListContent(
                    tasks = searchedTasksRequestState.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }

        sortState.data == Priority.NONE -> {
            if (allTasksRequestState is RequestState.Success) {
                HandleListContent(
                    tasks = allTasksRequestState.data,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
            }
        }

        sortState.data == Priority.LOW -> {
            HandleListContent(
                tasks = lowPriorityTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }

        sortState.data == Priority.HIGH -> {
            HandleListContent(
                tasks = highPriorityTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
    }
}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {
    if (tasks.any()) {
        DisplayTasks(tasks, navigateToTaskScreen, onSwipeToDelete)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {
    LazyColumn(modifier = Modifier, content = {
        items(items = tasks, itemContent = { task ->

            val dismissState = rememberDismissState(
                positionalThreshold = { threshold ->
                    (threshold * 0.1f).dp.toPx()
                }
            )
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                onSwipeToDelete(Action.DELETE, task)
            }

            val degrees by animateFloatAsState(
                targetValue = if (dismissState.targetValue == DismissValue.Default) {
                    0f
                } else {
                    -45f
                }
            )

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = { RedBackground(degrees = degrees) },
                dismissContent = {
                    TaskItem(
                        toDoTask = task,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                })
        })
    })
}


@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimensions.SMALL_PADDING)
            .background(HighPriorityColor),

        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = null,
            tint = Color.White,

            modifier = Modifier
                .padding(horizontal = Dimensions.LARGEST_PADDING)
                .rotate(degrees = degrees)
        )
    }
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
        allTasksRequestState = RequestState.Success(
            listOf(
                ToDoTask(0, "1", "2", Priority.HIGH),
                ToDoTask(0, "1", "2", Priority.HIGH)
            )
        ),
        searchAppBarState = SearchAppBarState.CLOSED,
        highPriorityTasks = emptyList(),
        sortState = RequestState.Success(Priority.NONE),
        lowPriorityTasks = emptyList(),
        searchedTasksRequestState = RequestState.Success(emptyList()),
        navigateToTaskScreen = {},
        onSwipeToDelete = { _, _ -> }
    )
}

@Preview(Constants.PREVIEW_DEFAULT, "List")
@Preview(
    Constants.PREVIEW_DARK_MODE,
    "List",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0x050505
)
@Composable
private fun EmptyListContentPreview() {
    ListContent(
        RequestState.Success(listOf()),
        searchAppBarState = SearchAppBarState.CLOSED,
        highPriorityTasks = emptyList(),
        sortState = RequestState.Success(Priority.NONE),
        lowPriorityTasks = emptyList(),
        searchedTasksRequestState = RequestState.Success(emptyList()),
        navigateToTaskScreen = {},
        onSwipeToDelete = { _, _ -> }
    )
}