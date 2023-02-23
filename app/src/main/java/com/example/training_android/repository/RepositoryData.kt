package com.example.training_android.repository

import androidx.lifecycle.LiveData
import com.example.training_android.dao.DAO
import com.example.training_android.model.Data

class RepositoryData(private val dao: DAO) {

    val getAllDataItem: LiveData<List<Data>> = dao.getAllDataItem()

    fun insertData(data: Data) {
        dao.insertData(data)
    }

    fun updateData(data: Data) {
        dao.updateData(data)
    }

    fun deleteData(data: Data) {
        dao.deleteData(data)
    }

    fun deleteAllData() {
        dao.deleteAll()
    }
}