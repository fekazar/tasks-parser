package edu.parsing.service

import edu.parsing.model.Task
import org.jsoup.Jsoup
import edu.parsing.repository.TaskRepository

class TaskService(
    private val taskRepository: TaskRepository,
    private val notificationService: NotificationService
) {
    fun getTasksForQuery(query: String) = Jsoup.connect("https://freelance.habr.com/tasks?q=$query")
        .get()
        .body()
        .getElementsByClass("task")
        .map {
            val titleEl = it.getElementsByClass("task__title").first()!!
            val title = titleEl.attributes()["title"]
            val id = titleEl.getElementsByTag("a").first()!!
                .attributes()["href"]
                .split("/").last().toInt()

            val price = it.getElementsByClass("count")
                .first()
                ?.text()
                ?.filter { it.isDigit() }
                ?.toInt()

            Task(id, title, price)
        }

    fun checkUpdates() {
        // todo: load queries from file
        val queries = listOf("java+kotlin")
        queries.asSequence()
            .flatMap { getTasksForQuery(it) } // todo: delay to not get 429
            .filter { taskRepository.findById(it.id) == null }
            .forEach {
                notificationService.notifyAbout(it)
                taskRepository.save(it)
            }
    }
}