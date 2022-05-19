package com.softmoon.dogsapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softmoon.dogsapp.data.local.entity.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(list: List<DogEntity>)

    @Query("SELECT * FROM dogs")
    fun getAll(): Flow<List<DogEntity>>

    @Query("SELECT count(id) FROM dogs")
    suspend fun getCount(): Int
}