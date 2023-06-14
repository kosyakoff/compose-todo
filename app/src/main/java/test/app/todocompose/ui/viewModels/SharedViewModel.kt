package test.app.todocompose.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.data.repositories.ToDoRepository
import test.app.todocompose.util.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTask = MutableStateFlow<List<ToDoTask>>(emptyList())

    val allTask = _allTask.asStateFlow()

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTask.collect { tasks ->
                _allTask.update { tasks }
            }
        }
    }
}