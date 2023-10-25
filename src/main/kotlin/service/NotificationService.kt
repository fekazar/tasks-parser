package service

import model.Task

interface NotificationService {
    fun notifyAbout(task: Task)
}