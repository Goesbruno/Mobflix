package com.goesbruno.mobflix.viewModel

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goesbruno.mobflix.data.repository.VideoRepository
import com.goesbruno.mobflix.data.repository.toVideo
import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.ui.state.FilteredVideoListScreenUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilteredVideoListScreenViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    private var selectedCategory: Category? = null

    private val _uiState: MutableStateFlow<FilteredVideoListScreenUiState> = MutableStateFlow(
        FilteredVideoListScreenUiState())
    val uiState get() = _uiState.asStateFlow()
    private val _youtubeIntent = MutableSharedFlow<Intent>()
    val youtubeIntent = _youtubeIntent.asSharedFlow()

    init {
        fetchVideo()
    }

    fun fetchVideo(){
        viewModelScope.launch {
            repository.videos.collect { videoEntities ->
                val allVideos = videoEntities.map { it.toVideo() }
                val filteredVideos = allVideos.filter { it.category == selectedCategory }
                _uiState.update { currentState ->
                    currentState.copy(
                        videoList = filteredVideos,
                        onTagClick = {},
                        selectedCategory = selectedCategory
                    )
                }
            }
        }
    }

    fun fetchCategory(categoryName: String) {
        val category = Category.entries.filter { it.name == categoryName }.first()
        selectedCategory = category
        fetchVideo()
    }

    fun openYoutubeVideo(videoUrl: String) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            _youtubeIntent.emit(intent)
        }
    }

}