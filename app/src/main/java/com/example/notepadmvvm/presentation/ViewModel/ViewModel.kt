package com.example.notepadmvvm.presentation.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notepadmvvm.data.DB
import com.example.notepadmvvm.data.DBModel
import com.example.notepadmvvm.domain.NotePadRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ViewModel(app: Application): AndroidViewModel(app) {

    val notes: LiveData<List<DBModel>>
    var repo: NotePadRepo


    init {
        val dao = DB.getDB(app).dbdao()
        repo = NotePadRepo(dao)
        notes = repo.allNotes
    }


    fun insert(d: DBModel) = viewModelScope.launch(Dispatchers.IO){
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        d.date = currentDate
        repo.insert(d)
    }

    fun delete(d: DBModel) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(d)
    }

    fun update(d: DBModel) = viewModelScope.launch(Dispatchers.IO){
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        d.date = currentDate
        repo.update(d)
    }





}