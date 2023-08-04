package com.example.notepadmvvm.domain

import androidx.lifecycle.LiveData
import com.example.notepadmvvm.data.DBDao
import com.example.notepadmvvm.data.DBModel

class NotePadRepo(private val dbDao: DBDao) {

    val allNotes: LiveData<List<DBModel>> = dbDao.getAll()

    suspend fun insert(dbModel: DBModel){
        dbDao.insert(dbModel)
    }

    suspend fun delete(dbModel: DBModel){
        dbDao.delete(dbModel)
    }

    suspend fun update(dbModel: DBModel){
        dbDao.update(dbModel)
    }



}