package test.app.todocompose.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import test.app.todocompose.R
import test.app.todocompose.components.PriorityItem
import test.app.todocompose.data.models.Priority
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.ui.theme.topAppBarBackgroundColor
import test.app.todocompose.ui.theme.topAppBarContentColor
import test.app.todocompose.ui.viewModels.SharedViewModel
import test.app.todocompose.util.SearchAppBarState
import test.app.todocompose.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(onSearchClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
            }, onSortClicked = {}, onDeleteClicked = {})
        }

        else -> {
            SearchAppBar(
                searchTextState,
                onTextChange = { newText -> sharedViewModel.searchTextState.value = newText },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit, onDeleteClicked: () -> Unit
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
        },
    )
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit, onSortClicked: (Priority) -> Unit, onDeleteClicked: () -> Unit
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
                    style = MaterialTheme.typography.bodyMedium,
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
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (text: String) -> Unit
) {

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.TOP_APP_BAR_HEIGHT),
        shadowElevation = Dimensions.SEARCH_APP_BAR_ELEVATION,
        color = MaterialTheme.colorScheme.topAppBarBackgroundColor
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.list_app_bar_search_placeholder_text),
                    color = Color.White,
                    modifier = Modifier.alpha(0.6f),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                fontSize = MaterialTheme.typography.labelMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.alpha(0.4f)) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.list_app_bar_search_icon_description_text),
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            if (text.isEmpty()) {
                                onCloseClicked()
                            } else {
                                onTextChange("")
                            }
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }

                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.list_app_bar_search_close_icon_description_text),
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked(text)
            })
        )
    }
}

@Composable
@Preview
fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortClicked = {}, onDeleteClicked = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar("Search", {}, {}, {})
}