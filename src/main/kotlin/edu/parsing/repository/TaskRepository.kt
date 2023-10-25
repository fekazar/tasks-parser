package edu.parsing.repository

import edu.parsing.Task

interface TaskRepository {
    fun findById(id: Int): Task?
    fun save(task: Task): Task
}
