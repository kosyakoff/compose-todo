package test.app.todocompose.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import test.app.todocompose.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(navigateToListScreen: (Action) -> Unit) {

    Scaffold(topBar = {
        TaskAppBar(navigateToListScreen = navigateToListScreen)
    }, content = { paddingValues -> })
}