package com.goesbruno.mobflix.ui.state

import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.model.Video

data class HomeScreenUIState(
    var categories: List<Category> = emptyList<Category>(),
    var videoList: List<Video> = emptyList<Video>()
)