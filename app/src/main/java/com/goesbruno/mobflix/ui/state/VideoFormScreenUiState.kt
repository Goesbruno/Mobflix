package com.goesbruno.mobflix.ui.state

import com.goesbruno.mobflix.model.Category

data class VideoFormScreenUiState(
    var url: String? = "",
    var selectedCategory: Category? = null,
    var categories: List<Category> = emptyList<Category>(),
    var expanded: Boolean = false,
    var onUrlChange: (String) -> Unit = {},
    var onCategorySelected: (Category) -> Unit = {},
    var onCategoryFieldClick: () -> Unit = {},
    var onCategoryFieldDismiss: () -> Unit = {},
    var screenTitle: String = "",
    var saveButtonText: String = "",
    var deleteButtonText: String = "",
    var isEditMode: Boolean = false,
    var urlError: String? = null,
    var categoryError: String? = null
) {
    val isSetCategory: Boolean get() = selectedCategory != null
}