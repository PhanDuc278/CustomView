package com.example.training_android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.training_android.database.DatabaseInstance
import com.example.training_android.model.Data
import com.example.training_android.repository.RepositoryData

class ViewModelData(application: Application) : AndroidViewModel(application) {

    val getAllData: LiveData<List<Data>>
    private val repository: RepositoryData

    init {
        val dao = DatabaseInstance.getDatabase(application).dataDao()
        repository = RepositoryData(dao)
        getAllData = repository.getAllDataItem
    }

    fun insertData(data: Data) {
        repository.insertData(data)
    }

    fun deleteData(data: Data) {
        repository.deleteData(data)
    }

    fun updateData(data: Data) {
        repository.updateData(data)
    }

    fun deleteAllData() {
        repository.deleteAllData()
    }

}