package com.goesbruno.mobflix.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goesbruno.mobflix.data.database.entitiy.VideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Query("SELECT * FROM VideoEntity")
    fun findAllVideos(): Flow<List<VideoEntity>>

    @Query("SELECT * FROM VideoEntity WHERE id IN (:videoId)")
    fun findById(videoId: String): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(video: VideoEntity)

    @Delete
    suspend fun delete(video: VideoEntity)

}