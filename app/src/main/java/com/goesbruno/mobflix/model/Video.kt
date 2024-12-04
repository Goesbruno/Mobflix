package com.goesbruno.mobflix.model

import java.util.UUID

data class Video(
        val id: String = UUID.randomUUID().toString() ,
        val url: String,
        val category: Category
    )

