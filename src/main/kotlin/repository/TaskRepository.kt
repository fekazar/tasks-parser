package repository

import model.Task

interface TaskRepository {
    fun findById(id: Int): Task?
    fun save(task: Task): Task
}
