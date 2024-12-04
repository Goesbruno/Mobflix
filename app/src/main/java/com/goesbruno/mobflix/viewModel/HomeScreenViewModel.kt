package com.goesbruno.mobflix.viewModel

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goesbruno.mobflix.data.repository.VideoRepository
import com.goesbruno.mobflix.data.repository.toVideo
import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.ui.state.HomeScreenUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: VideoRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUIState> =
        MutableStateFlow(HomeScreenUIState())
    val uiState get() = _uiState.asStateFlow()
    private val _youtubeIntent = MutableSharedFlow<Intent>()
    val youtubeIntent = _youtubeIntent.asSharedFlow()

    init {
        fetchVideos()
    }

    fun fetchVideos() {
        viewModelScope.launch {
            repository.videos.collect { videoEntities ->
                val videos = videoEntities.map { it.toVideo() }
                _uiState.update { currentState ->
                    currentState.copy(
                        videoList = videos,
                        categories = Category.entries.toList()
                    )
                }
            }
        }
    }



    fun openYoutubeVideo(videoUrl: String) {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            _youtubeIntent.emit(intent)
        }
    }
}
