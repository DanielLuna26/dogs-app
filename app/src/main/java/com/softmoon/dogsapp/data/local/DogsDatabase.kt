package com.softmoon.dogsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.softmoon.dogsapp.data.local.daos.DogsDao
import com.softmoon.dogsapp.data.local.entity.DogEntity

@Database(
    entities = [
        DogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DogsDatabase: RoomDatabase() {

    abstract fun dogsDao(): DogsDao

    companion object {
        @Volatile
        private var instance: DogsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room
                .databaseBuilder(
                    context,
                    DogsDatabase::class.java,
                    "dogs.db"
                )
                .build()
    }
}