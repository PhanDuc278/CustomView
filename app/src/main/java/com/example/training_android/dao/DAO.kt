package com.example.training_android.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.training_android.model.Data

@Dao
interface DAO {
    //Get all data from database
    @Query("SELECT * FROM data ORDER BY id DESC")
    fun getAllDataItem(): LiveData<List<Data>>

    //Insert data to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: Data)

    //Update data
    @Update
    fun updateData(data: Data)

    //Delete data
    @Delete
    fun deleteData(data: Data)

    //Delete table in database
    @Query("DELETE FROM data")
    fun deleteAll()


}