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
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.data.repositories.ToDoRepository
import test.app.todocompose.util.Constants
import test.app.todocompose.util.RequestState
import test.app.todocompose.util.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repository: ToDoRepository) : ViewModel() {

    private val _allTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)

    val selectedTask = _selectedTask.asStateFlow()
    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")
    val allTask = _allTask.asStateFlow()

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    fun getAllTasks() {
        _allTask.update { RequestState.Loading }
        try {
            viewModelScope.launch {
                repository.getAllTask.collect { tasks ->
                    _allTask.update { RequestState.Success(tasks) }
                }
            }
        } catch (ex: Throwable) {
            _allTask.update { RequestState.Error(error = ex) }
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect { task ->
                _selectedTask.update { task }
            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < Constants.MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }
}