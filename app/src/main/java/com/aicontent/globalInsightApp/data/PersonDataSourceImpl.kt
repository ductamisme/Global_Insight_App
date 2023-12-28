package com.aicontent.globalInsightApp.data
//
//class PersonDataSourceImpl(
//    db: PersonDatabase
//): PersonDataSource {
//
//    private val queries = db.personEntityQueries
//
//    override suspend fun getPersonById(id: Long): PersonEntity? {
//        return withContext(Dispatchers.IO) {
//            queries.getPersonById(id).executeAsOneOrNull()
//        }
//    }
//
//    override fun getAllPersons(): Flow<List<PersonEntity>> {
//        return queries.getAllPersons().asFlow().mapToList()
//    }
//
//    override suspend fun deletePersonById(id: Long) {
//        withContext(Dispatchers.IO) {
//            queries.deletePersonById(id)
//        }
//    }
//
//    override suspend fun insertPerson(firstName: String, lastName: String, id: Long?) {
//        withContext(Dispatchers.IO) {
//            queries.insertPerson(id, firstName, lastName)
//        }
//    }
//}