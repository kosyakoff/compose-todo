package test.app.todocompose.ui.screens.task

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import test.app.todocompose.R
import test.app.todocompose.ui.theme.topAppBarBackgroundColor
import test.app.todocompose.ui.theme.topAppBarContentColor
import test.app.todocompose.util.Action

@Composable
fun TaskAppBar(navigateToListScreen: (Action) -> Unit) {
    NewTaskAppBar(navigateToListScreen = navigateToListScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(navigateToListScreen: (Action) -> Unit) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(R.string.task_app_bar_new_task_add_task_text),
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
        ),
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )
}

@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, contentDescription = null,
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check, contentDescription = null,
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Preview("Default")
@Preview("DarkMode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})
}