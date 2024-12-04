package com.goesbruno.mobflix.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.goesbruno.mobflix.R
import com.goesbruno.mobflix.util.extractYouTubeVideoId

@Composable
fun YouTubeThumbnail(
    videoUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    placeholder: Painter?
) {
    val videoId = extractYouTubeVideoId(videoUrl)
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/maxresdefault.jpg"

    AsyncImage (
        model = thumbnailUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder
    )
}



@Preview
@Composable
private fun YouTubeThumbnailPreview() {
    YouTubeThumbnail(
        videoUrl = "asdasdas",
        contentDescription = null,
        placeholder = painterResource(R.drawable.placeholder)
    )
}

