package edu.parsing.service

import edu.parsing.model.Task
import java.util.concurrent.Executors

// todo: make html formatting
fun Task.toTextMessage() = """
    <b>$header </b>
    <b>Цена: </b> $price
    <a href="https://freelance.habr.com/tasks/$id">На страницу заказа </a>
""".trimIndent()

class SubprocessNotifyService : NotificationService {
    private val sendThread = Executors.newSingleThreadExecutor()

    override fun notifyAbout(task: Task) {
        sendThread.submit {
            ProcessBuilder("telegram-send", "--format", "html", "${task.toTextMessage()}")
                .directory(null)
                .redirectInput(ProcessBuilder.Redirect.INHERIT)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start()

            Thread.sleep(3000)
        }
    }
}