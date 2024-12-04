package com.goesbruno.mobflix.data.database.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goesbruno.mobflix.model.Category
import java.util.UUID


@Entity
data class VideoEntity (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val url: String,
    val category: Category
)