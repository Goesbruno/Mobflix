package com.goesbruno.mobflix.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goesbruno.mobflix.data.database.dao.VideoDao
import com.goesbruno.mobflix.data.database.entitiy.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class MobflixDatabase : RoomDatabase(){

    abstract fun videoDao(): VideoDao

}