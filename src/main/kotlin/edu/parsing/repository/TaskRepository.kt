package edu.parsing.repository

import edu.parsing.model.Task

interface TaskRepository {
    fun findById(id: Int): Task?
    fun save(task: Task): Task
}
