package test.app.todocompose.ui.screens.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import test.app.todocompose.R
import test.app.todocompose.ui.theme.fabBackgroundColor

@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            ListAppBar()
        },
        content = { values -> values.calculateTopPadding() },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        containerColor = MaterialTheme.colorScheme.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
@Preview
private fun ListScreenPreview() {
    ListScreen(navigateToTaskScreen = {})
}