package edu.parsing.repository

import edu.parsing.model.Query

interface QueryRepository {
    fun findAll(): List<Query>
}