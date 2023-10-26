package edu.parsing.repository

import edu.parsing.model.Query
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

class FileQueryRepository(val fileName: String) : QueryRepository {
    override fun findAll() = Files.readAllLines(Path.of(fileName), Charset.defaultCharset())
        .asSequence()
        .map { it.trim() }
        .map { Query(it) }
        .toList()
}