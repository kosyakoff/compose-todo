package test.app.todocompose.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import test.app.todocompose.data.models.Priority
import test.app.todocompose.data.models.ToDoTask
import test.app.todocompose.data.repositories.DataStoreRepository
import test.app.todocompose.data.repositories.ToDoRepository
import test.app.todocompose.util.Action
import test.app.todocompose.util.Constants
import test.app.todocompose.util.RequestState
import test.app.todocompose.util.SearchAppBarState
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)

    private val _allTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)

    private val _sortState: MutableStateFlow<RequestState<Priority>> =
        MutableStateFlow(RequestState.Idle)

    val sortState = _sortState.asStateFlow()
    val selectedTask = _selectedTask.asStateFlow()
    val allTask = _allTask.asStateFlow()
    val searchedTasks = _searchedTasks.asStateFlow()

    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set

    var searchTextState by mutableStateOf("")
        private set

    var id by mutableIntStateOf(0)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set

    var action by mutableStateOf(Action.NO_ACTION)
        private set

    val lowPriorityTasks: StateFlow<List<ToDoTask>> = repository.sortByLowPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val highPriorityTasks: StateFlow<List<ToDoTask>> = repository.sortByHighPriority.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    init {
        getAllTasks()
        readSortState()
    }


    fun updateDescription(newText: String) {
        description = newText
    }

    fun updateSearchTextState(newText: String) {
        searchTextState = newText
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateSearchAppBarState(newState: SearchAppBarState) {
        searchAppBarState = newState
    }

    fun searchTasks(searchQuery: String) {
        _searchedTasks.update { RequestState.Loading }
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery).collect { tasks ->
                    _searchedTasks.update { RequestState.Success(tasks) }
                }
            }
        } catch (ex: Throwable) {
            _searchedTasks.update { RequestState.Error(error = ex) }
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
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
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < Constants.MAX_TITLE_LENGTH) {
            title = newTitle
        }
    }

    fun validateFields(): Boolean = title.isNotEmpty() && description.isNotEmpty()

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTasks()
            }

            Action.UNDO -> {
                addTask()
            }

            Action.NO_ACTION -> {}
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(toDoTask)
        }
        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateTask(toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteTask(toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    private fun getAllTasks() {
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

    private fun readSortState() {
        _sortState.update { RequestState.Loading }
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState.map { Priority.valueOf(it) }.collect { state ->
                    _sortState.update { RequestState.Success(state) }
                }
            }
        } catch (ex: Throwable) {
            _sortState.update { RequestState.Error(error = ex) }
        }
    }
}