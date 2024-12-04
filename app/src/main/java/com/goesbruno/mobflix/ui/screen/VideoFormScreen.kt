package com.goesbruno.mobflix.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goesbruno.mobflix.R
import com.goesbruno.mobflix.ui.components.CategoryTag
import com.goesbruno.mobflix.ui.components.CategoryTextField
import com.goesbruno.mobflix.ui.components.YouTubeThumbnail
import com.goesbruno.mobflix.ui.state.VideoFormScreenUiState
import com.goesbruno.mobflix.ui.theme.Black
import com.goesbruno.mobflix.ui.theme.Blue
import com.goesbruno.mobflix.ui.theme.BlueDark
import com.goesbruno.mobflix.ui.theme.MobflixTheme
import com.goesbruno.mobflix.ui.theme.Red
import com.goesbruno.mobflix.viewModel.VideoFormViewModel

// TODO: Campo url com elipsize



@Composable
fun VideoFormScreen(
    viewModel: VideoFormViewModel,
    onSaveClick: (urlError: String?, categoryError: String?) -> Unit = {a, b ->},
    onDeleteClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    VideoFormScreen(
        state = state,
        modifier = Modifier,
        onSaveClick = {urlError, categoryError ->
            viewModel.save()
            onSaveClick(urlError, categoryError)
        },
        onDeleteClick = {
            viewModel.delete()
            onDeleteClick()
        }
    )
}

@Composable
fun VideoFormScreen(
    state: VideoFormScreenUiState,
    onSaveClick: (urlError: String?, categoryError: String?) -> Unit = {a, b ->},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(
        modifier
            .fillMaxSize()
            .background(Black)
            .padding(36.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val url = state.url ?: ""
        val expanded = state.expanded
        val selectedCategory = state.selectedCategory
        val categories = state.categories
        val onUrlChange = state.onUrlChange
        val onCategorySelected = state.onCategorySelected
        val onCategoryFieldClick = state.onCategoryFieldClick
        val onCategoryFieldDismiss = state.onCategoryFieldDismiss
        val isSetCategory = state.isSetCategory
        val placeholder = painterResource(R.drawable.placeholder)
        val title = state.screenTitle
        val saveButtonText = state.saveButtonText
        val deleteButtonText = state.deleteButtonText
        val isEditMode = state.isEditMode

        Text(
            text = title,
            modifier = Modifier,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily.SansSerif
        )
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "URL:",
                    modifier = Modifier,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.SansSerif
                )

                TextField(
                    value = url,
                    onValueChange = onUrlChange,
                    Modifier
                        .heightIn(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                            Text(
                                text = "Ex: N3h5A0oAzsk",
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400)
                            )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedLabelColor = Color.LightGray,
                        focusedContainerColor = BlueDark,
                        unfocusedContainerColor = BlueDark,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        unfocusedPlaceholderColor = Color.LightGray,
                        focusedPlaceholderColor = Color.White
                    )

                )
            }
        }

        CategoryTextField(
            categories = categories,
            onCategorySelected = onCategorySelected,
            selectedCategory = selectedCategory,
            expanded = expanded,
            onIconClick = onCategoryFieldClick,
            onDismiss = onCategoryFieldDismiss,
            isSetCategory = isSetCategory
        )

        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Preview",
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700),
                    color = Color.White
                )
                if (url.isNotBlank()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CategoryTag(category = selectedCategory)
                        YouTubeThumbnail(
                            videoUrl = url,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .height(180.dp)
                                .fillMaxWidth(),
                            contentDescription = null,
                            placeholder = placeholder
                        )
                    }
                } else {
                    Image(
                        painter = placeholder,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }
            }
        }

        Column (
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Button(
                onClick = {
                    onSaveClick(state.urlError, state.categoryError)
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                )
            ) {
                Text(
                    text = saveButtonText,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                )
            }

            if (isEditMode) {
                Button(
                    onClick = {
                        onDeleteClick()
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red
                    )
                ) {
                    Text(
                        text = deleteButtonText,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(400)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun VideoFormScreenPreview() {
    MobflixTheme {
        VideoFormScreen( state = VideoFormScreenUiState())
    }
}