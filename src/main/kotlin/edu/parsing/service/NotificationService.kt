package edu.parsing.service

import edu.parsing.model.Task

interface NotificationService {
    fun notifyAbout(task: Task)
}