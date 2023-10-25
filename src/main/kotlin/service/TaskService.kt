package service

import model.Task
import org.jsoup.Jsoup
import repository.TaskRepository

class TaskService(val taskRepository: TaskRepository) {
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

        queries.forEach { task ->
            // todo: send message for every not persisted task
            val tasks = getTasksForQuery(task)
                .filter { taskRepository.findById(it.id) == null }
        }
    }
}