package test.app.todocompose.data.repositories

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import test.app.todocompose.data.ToDoDao
import test.app.todocompose.data.models.ToDoTask
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTask: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority: Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> = toDoDao.getSelectedTask(taskId)

    suspend fun addTask(toDoTask: ToDoTask) = toDoDao.addTask(toDoTask)

    suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)

    suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)

    suspend fun deleteAllTasks() = toDoDao.deleteAllTasks()

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> =
        toDoDao.searchDataBase(searchQuery)

}