package test.app.todocompose.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import test.app.todocompose.R
import test.app.todocompose.components.PriorityItem
import test.app.todocompose.data.models.Priority
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.ui.theme.Typography
import test.app.todocompose.ui.theme.topAppBarBackgroundColor
import test.app.todocompose.ui.theme.topAppBarContentColor

@Composable
fun ListAppBar() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.list_title),
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
        ),
        actions = {
            ListBarActions(onSearchClicked, onSortClicked, onDeleteClicked)
        }
    )
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = onSearchClicked) {
        Icon(
            Icons.Filled.Search,
            contentDescription = stringResource(R.string.list_action_search),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(R.string.list_action_sort),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = {
                PriorityItem(priority = Priority.LOW)
            }, onClick = {
                expanded = false
                onSortClicked(Priority.LOW)
            })
            DropdownMenuItem(text = {
                PriorityItem(priority = Priority.HIGH)
            }, onClick = {
                expanded = false
                onSortClicked(Priority.HIGH)
            })
            DropdownMenuItem(text = {
                PriorityItem(priority = Priority.NONE)
            }, onClick = {
                expanded = false
                onSortClicked(Priority.NONE)
            })

        }
    }
}

@Composable
fun DeleteAllAction(onDeleteClicked: () -> Unit) {

    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(R.string.list_action_delete_all),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = R.string.list_action_delete_all),
                    style = Typography.bodyMedium,
                    modifier = Modifier.padding(start = Dimensions.LARGE_PADDING)
                )
            }, onClick = {
                expanded = false
                onDeleteClicked()
            })
        }
    }
}

@Composable
@Preview
fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}