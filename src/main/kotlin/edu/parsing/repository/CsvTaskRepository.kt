package edu.parsing.repository

import edu.parsing.model.Task
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

// Writes in format: id,header,price
class CsvRepository(val fileName: String) : TaskRepository {
    companion object {
        const val ID = "id"
        const val HEADER = "header"
        const val PRICE = "price"
    }

    private val format = CSVFormat.Builder.create()
        .setHeader(ID, HEADER, PRICE)
        .setSkipHeaderRecord(true)
        .build()

    private val csvWriter = CSVPrinter(BufferedWriter(FileWriter(fileName, true)), format)

    // todo: close resources
    private val tasks = CSVParser(BufferedReader(FileReader(fileName)), format)
        .records
        .map { Task(it[0].toInt(), it[1], it[2].toIntOrNull()) }
        .groupingBy { it.id }
        .reduce { _, _, el -> el }
        .toMutableMap()

    override fun findById(id: Int): Task? = tasks[id]

    override fun save(task: Task): Task {
        if (tasks.containsKey(task.id))
            return task

        tasks[task.id] = task

        csvWriter.printRecord(task.id, task.header, task.price)
        csvWriter.flush()
        return task
    }
}
