package edu.parsing.service

import edu.parsing.model.Task
import java.util.concurrent.Executors

// todo: make html formatting
fun Task.toTextMessage() = "'New task: $this'"

class SubprocessNotifyService : NotificationService {
    private val sendThread = Executors.newSingleThreadExecutor()

    override fun notifyAbout(task: Task) {
        sendThread.submit {
            ProcessBuilder("telegram-send", task.toTextMessage())
                .directory(null)
                .redirectInput(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()

            Thread.sleep(3000)
        }
    }
}