package com.goesbruno.mobflix.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goesbruno.mobflix.R
import com.goesbruno.mobflix.model.Video
import com.goesbruno.mobflix.ui.components.CategoryTag
import com.goesbruno.mobflix.ui.components.EmptyList
import com.goesbruno.mobflix.ui.components.YouTubeThumbnail
import com.goesbruno.mobflix.ui.state.FilteredVideoListScreenUiState
import com.goesbruno.mobflix.ui.theme.Black
import com.goesbruno.mobflix.ui.theme.Blue
import com.goesbruno.mobflix.viewModel.FilteredVideoListScreenViewModel

@Composable
fun FilteredVideoListScreen(
    viewModel: FilteredVideoListScreenViewModel,
    onVideoLongClick: (video: Video) -> Unit = {},
    onYoutubeCall: (video: Video) -> Unit = {},
    onTagClick: () -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    FilteredVideoListScreen(
        state = state,
        onVideoLongClick = { onVideoLongClick(it) },
        onVideoClick = { onYoutubeCall(it) },
        onTagClick = {onTagClick()}
    )
}

@Composable
fun FilteredVideoListScreen(
    modifier: Modifier = Modifier.Companion,
    state: FilteredVideoListScreenUiState = FilteredVideoListScreenUiState(),
    onVideoLongClick: (video: Video) -> Unit = {},
    onVideoClick: (video: Video) -> Unit = {},
    onTagClick: () -> Unit = {}
) {
    val placeholder = painterResource(R.drawable.placeholder)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.Companion.height(38.dp))
        Text(
            text = "MOBFLIX",
            Modifier.Companion.padding(bottom = 8.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight(400),
            fontFamily = FontFamily(Font(R.font.bebas_neue)),
            color = Blue
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Box(
                Modifier.Companion
                    .fillMaxWidth()
            ) {
                LazyRow(
                    Modifier.Companion
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    verticalAlignment = Alignment.Companion.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    item {
                        Text(
                            text = "Categoria:",
                            fontSize = 32.sp,
                            fontWeight = FontWeight(700),
                            color = Color.Companion.White
                        )
                    }
                    item {
                        CategoryTag(
                            category = state.selectedCategory,
                            Modifier.Companion
                                .clickable { onTagClick() }
                        )
                    }

                }
            }
            if (state.videoList.isNotEmpty()) {
                //Video list
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 35.dp),
                    horizontalAlignment = Alignment.Companion.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    state.videoList.forEach { video ->
                        val category = video.category
                        val url = video.url
                        item {
                            Box(
                                Modifier.Companion
                                    .height(220.dp),
                                contentAlignment = Alignment.Companion.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CategoryTag(category)
                                    YouTubeThumbnail(
                                        videoUrl = url,
                                        contentDescription = null,
                                        modifier = Modifier.Companion
                                            .clip(RoundedCornerShape(8.dp))
                                            .width(320.dp)
                                            .height(180.dp)
                                            .pointerInput(Unit) {
                                                detectTapGestures(
                                                    onTap = {
                                                        onVideoClick(video)
                                                    },
                                                    onLongPress = {
                                                        onVideoLongClick(video)
                                                    }
                                                )
                                            },
                                        placeholder = placeholder
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                EmptyList("Nenhum vídeo cadastrado nessa categoria")
            }
        }
    }
}

@Preview
@Composable
private fun FilteredVideoListScreenPreview() {
    FilteredVideoListScreen(
        state = FilteredVideoListScreenUiState()
    )

}