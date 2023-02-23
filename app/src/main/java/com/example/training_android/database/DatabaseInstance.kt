package com.example.training_android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.training_android.dao.DAO
import com.example.training_android.model.Data

@Database(entities = [Data::class], version = 1, exportSchema = true)
abstract class DatabaseInstance : RoomDatabase() {
    abstract fun dataDao(): DAO

    companion object {
        @Volatile
        private var INSTANCE: DatabaseInstance? = null

        fun getDatabase(context: Context): DatabaseInstance {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInstance::class.java,
                    "data_database"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}