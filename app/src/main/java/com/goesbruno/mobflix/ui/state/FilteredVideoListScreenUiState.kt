package com.goesbruno.mobflix.ui.state

import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.model.Video

data class FilteredVideoListScreenUiState (
    var videoList: List<Video> = emptyList<Video>(),
    var selectedCategory: Category? = null,
    var onTagClick: (category: Category) -> Unit = {}
)