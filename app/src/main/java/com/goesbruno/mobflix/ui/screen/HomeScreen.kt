package com.goesbruno.mobflix.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.zIndex
import com.goesbruno.mobflix.R
import com.goesbruno.mobflix.data.sampleVideos
import com.goesbruno.mobflix.model.Category
import com.goesbruno.mobflix.model.Video
import com.goesbruno.mobflix.ui.components.CategoryTag
import com.goesbruno.mobflix.ui.components.EmptyList
import com.goesbruno.mobflix.ui.components.YouTubeThumbnail
import com.goesbruno.mobflix.ui.state.HomeScreenUIState
import com.goesbruno.mobflix.ui.theme.Black
import com.goesbruno.mobflix.ui.theme.Blue
import com.goesbruno.mobflix.ui.theme.MobflixTheme
import com.goesbruno.mobflix.ui.theme.Purple
import com.goesbruno.mobflix.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    onVideoLongClick: (video: Video) -> Unit = {},
    onYoutubeCall: (video: Video) -> Unit = {},
    onFabClick: () -> Unit = {},
    onTagClick: (category: Category) -> Unit = {}
) {
    val state = viewModel.uiState.collectAsState().value

    HomeScreen(
        state = state,
        onVideoLongClick = { onVideoLongClick(it) },
        onVideoClick = { onYoutubeCall(it) },
        onFeatButtonClick = { onYoutubeCall(it) },
        onFabClick = { onFabClick() },
        onTagClick = { onTagClick(it) }
    )
}

@Composable
fun HomeScreen(
    state: HomeScreenUIState = HomeScreenUIState(),
    onVideoLongClick: (video: Video) -> Unit = {},
    onVideoClick: (video: Video) -> Unit = {},
    onFeatButtonClick: (video: Video) -> Unit = {},
    onFabClick: () -> Unit = {},
    onTagClick: (category: Category) -> Unit = {}
) {
    val placeholder = painterResource(R.drawable.placeholder)

    Box(
        Modifier
            .fillMaxSize()
            .background(Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.height(38.dp))
            Text(
                text = "MOBFLIX",
                Modifier.padding(bottom = 8.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily(Font(R.font.bebas_neue)),
                color = Blue
            )

            if (state.videoList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(138.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val featVideo = state.videoList.first()
                        YouTubeThumbnail(
                            videoUrl = featVideo.url,
                            Modifier
                                .fillMaxWidth()
                                .height(138.dp),
                            contentDescription = null,
                            placeholder = placeholder
                        )
                        Column {

                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(25))
                                    .widthIn(128.dp)
                                    .background(Blue)
                                    .padding(9.dp)
                                    .clickable { onFeatButtonClick(featVideo) }
                            ) {
                                Text(
                                    "Assista agora",
                                    Modifier.align(Alignment.Center),
                                    color = Color.White
                                )
                            }
                            Spacer(Modifier.height(20.dp))
                        }
                    }

                    //Category list
                    LazyRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 28.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        contentPadding = PaddingValues(horizontal = 35.dp)

                    ) {
                        state.categories.forEach { category ->
                            item {
                                CategoryTag(
                                    category = category,
                                    modifier = Modifier
                                        .clickable {
                                            onTagClick(category)
                                        }
                                )
                            }
                        }
                    }


                    //Video list
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 35.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        state.videoList.forEach { video ->
                            val category = video.category
                            val url = video.url
                            item {
                                Box(
                                    Modifier
                                        .height(220.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        CategoryTag(category)
                                        YouTubeThumbnail(
                                            videoUrl = url,
                                            contentDescription = null,
                                            modifier = Modifier
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
                }


            } else {
                EmptyList("Nenhum vídeo cadastrado")
            }
        }
        FloatingActionButton(
            onClick = onFabClick,
            shape = CircleShape,
            modifier = Modifier
                .height(52.dp)
                .width(52.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .absoluteOffset(x = (-35).dp, y = (-35).dp),
            contentColor = Color.White,
            containerColor = Purple,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier
                    .height(26.dp)
                    .width(26.dp)
            )
        }
    }


}

@Preview
@Composable
private fun HomeScreenPreviewEmptyList() {
    MobflixTheme {

        HomeScreen()
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MobflixTheme {

        HomeScreen(
            HomeScreenUIState(
                videoList = sampleVideos
            )
        )
    }
}


