package com.goesbruno.mobflix.data

import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.model.Video

val sampleVideos = mutableListOf<Video>(
    Video(
        url = "https://www.youtube.com/watch?v=WgrUbqgwfEo",
        category = Category.FRONT_END
    ),
    Video(
        url = "https://www.youtube.com/watch?v=pcnfjJG3jY4",
        category = Category.PROGRAMACAO
    ),
    Video(
        url = "https://www.youtube.com/watch?v=fmu1LQvZhms",
        category = Category.MOBILE
    )
)