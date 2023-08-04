package com.example.notepadmvvm.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
class DBModel(

    @ColumnInfo(name="title")
    var title: String,

    @ColumnInfo(name="text")
    var text: String,


    @ColumnInfo(name="date")
    var date: String

) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}