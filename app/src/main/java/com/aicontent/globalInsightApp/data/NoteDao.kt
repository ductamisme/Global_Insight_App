package com.aicontent.globalInsightApp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountryDao {

    @Insert
    fun insert(country: Country)

    @Update
    fun update(country: Country)

    @Delete
    fun delete(country: Country)

    @Query("delete from note_table")
    fun deleteAllNotes()

//    @Query("select * from note_table order by A desc")
//    fun getAllNotes(): LiveData<List<Country>>
}