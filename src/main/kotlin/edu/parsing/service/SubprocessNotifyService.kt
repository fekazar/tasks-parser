package edu.parsing.service

import edu.parsing.model.Task

// todo: make html formatting
fun Task.toTextMessage() = "'New task: $this'"

class SubprocessNotifyService : NotificationService {
    // todo: add tasks to pool to send messages steadily
    override fun notifyAbout(task: Task) {
        ProcessBuilder("telegram-send", task.toTextMessage())
            .directory(null)
            .redirectInput(ProcessBuilder.Redirect.INHERIT)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .start()
    }
}