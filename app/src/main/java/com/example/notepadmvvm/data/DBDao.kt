package com.example.notepadmvvm.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DBDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(DBModel: DBModel)

    @Update
    suspend fun update(DBModel: DBModel)

    @Delete
    suspend fun delete(DBModel: DBModel)

    @Query("UPDATE DBModel Set title= :title, text =:text WHERE id = :id")
    suspend fun secondUpdate(id: Int?, title: String?, text: String? )

    @Query("SELECT * FROM DBModel order by id ASC")
    fun getAll(): LiveData<List<DBModel>>

}