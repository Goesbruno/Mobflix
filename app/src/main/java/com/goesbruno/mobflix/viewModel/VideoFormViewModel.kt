package com.goesbruno.mobflix.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goesbruno.mobflix.data.repository.VideoRepository
import com.goesbruno.mobflix.data.repository.toVideo
import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.model.Video
import com.goesbruno.mobflix.ui.state.VideoFormScreenUiState
import com.goesbruno.mobflix.util.FormErrors
import com.goesbruno.mobflix.util.isValidYouTubeUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoFormViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    private var videoId: String = "new"

    private val _uiState: MutableStateFlow<VideoFormScreenUiState> = MutableStateFlow(
        VideoFormScreenUiState()
    )
    val uiState get() = _uiState.asStateFlow()


    init {
        updateFormState()
    }

    private fun updateFormState() {
        setFormMode()
        viewModelScope.launch {
            _uiState.update { currentState ->
                val (screenTitle, saveButtonText, deleteButtonText) = if (currentState.isEditMode) {
                    Triple("Edite o vídeo", "Alterar", "Remover")
                } else {
                    Triple("Cadastre um vídeo", "Cadastrar", "")
                }
                currentState.copy(
                    url = if (currentState.isEditMode)
                        findVideoById(videoId).url else null,
                    selectedCategory = if (currentState.isEditMode)
                        findVideoById(videoId).category else null,
                    categories = Category.entries,
                    onUrlChange = { newUrl ->
                        _uiState.update {
                            it.copy(
                                url = newUrl,
                                urlError = if (newUrl != null) {
                                    if (isValidYouTubeUrl(newUrl) == true) null
                                    else FormErrors.YT_LINK_ERROR
                                } else FormErrors.NULL_URL_ERROR
                            )
                        }
                    },
                    onCategorySelected = { selectedCategory ->
                        _uiState.update {
                            it.copy(
                                expanded = false,
                                selectedCategory = selectedCategory,
                                categoryError = null
                            )
                        }
                    },
                    onCategoryFieldClick = {
                        _uiState.update { it.copy(expanded = !it.expanded) }
                    },
                    onCategoryFieldDismiss = {
                        _uiState.update { it.copy(expanded = false) }
                    },
                    screenTitle = screenTitle,
                    saveButtonText = saveButtonText,
                    deleteButtonText = deleteButtonText,
                    urlError = if (currentState.isEditMode == true)
                        null else FormErrors.NULL_URL_ERROR,
                    categoryError = if (currentState.selectedCategory != null)
                        null else FormErrors.NULL_CATEGORY_ERROR
                )
            }
        }

    }

    private fun updateErrorState() {
        _uiState.update { currentState ->
            val url = currentState.url
            val category = currentState.selectedCategory
            currentState.copy(
                urlError = when {
                    url == null -> FormErrors.NULL_URL_ERROR
                    isValidYouTubeUrl(url) == false -> FormErrors.YT_LINK_ERROR
                    else -> null
                },
                categoryError = if (category == null)
                    FormErrors.NULL_CATEGORY_ERROR
                else null
            )
        }
    }

    private fun setFormMode() {
        _uiState.update { currentState ->
            currentState.copy(
                isEditMode = videoId != "new"
            )
        }
    }

    suspend fun findVideoById(videoId: String): Video {
        return withContext(Dispatchers.IO) {
            repository.findById(videoId).toVideo()
        }
    }

    fun save() {
        viewModelScope.launch {
            updateErrorState()
            _uiState.value.let { uiState ->
                val category = uiState.selectedCategory
                val url = uiState.url
                when {
                    category == null -> {
                        _uiState.update { it.copy(categoryError = FormErrors.NULL_CATEGORY_ERROR) }
                    }

                    url == null -> {
                        _uiState.update { it.copy(urlError = FormErrors.NULL_URL_ERROR) }
                    }

                    isValidYouTubeUrl(url) == false -> {
                        _uiState.update { it.copy(urlError = FormErrors.YT_LINK_ERROR) }
                    }

                    else -> {
                        val video = if (videoId != "new") {
                            Video(
                                id = videoId,
                                url = url,
                                category = category
                            )
                        } else {
                            Video(
                                url = url,
                                category = category
                            )
                        }
                        repository.save(video)
                        _uiState.update {
                            it.copy(urlError = null, categoryError = null)
                        }
                    }
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            val video = findVideoById(videoId)
            repository.delete(video)
        }
    }

    fun updateVideoId(newVideoId: String) {
        videoId = newVideoId
        updateFormState()
    }

}


