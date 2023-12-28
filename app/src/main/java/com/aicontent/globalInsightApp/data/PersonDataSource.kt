package com.aicontent.globalInsightApp.data

import com.aicontent.globalInsightApp.entity.modelAll.entity
import com.aicontent.globalInsightApp.entity.modelAll.entityItem
import java.util.concurrent.Flow

interface PersonDataSource {

    suspend fun getPersonById(id: Long): entityItem?

    fun getAllPersons(): entity

    suspend fun deletePersonById(id: Long)

    suspend fun insertPerson(firstName: String, lastName: String, id: Long? = null)
}