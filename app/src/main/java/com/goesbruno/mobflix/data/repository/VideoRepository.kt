package com.goesbruno.mobflix.data.repository

import com.goesbruno.mobflix.data.database.dao.VideoDao
import com.goesbruno.mobflix.data.database.entitiy.VideoEntity
import com.goesbruno.mobflix.model.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class VideoRepository(
    private val dao: VideoDao
) {

    val videos get() = dao.findAllVideos()

    suspend fun save(video: Video) = withContext(Dispatchers.IO) {
        dao.save(video.toVideoEntity())
    }

    suspend fun delete(video: Video) = withContext(Dispatchers.IO) {
        dao.delete(video.toVideoEntity())
    }

    suspend fun findById(videoId: String): VideoEntity {
        val entity = dao.findById(videoId).first().first()
        return entity
    }


//    fun generateNewId(): Long {
//        return (_videos.value.maxOfOrNull { it.id } ?: 0L) + 1
//    }

//    val existingVideoIndex = _videos.value.indexOfFirst { it.id == video.id }
//    if (existingVideoIndex >= 0) {
//        _videos.value = _videos.value.toMutableList().apply {
//            set(existingVideoIndex, video)
//        }
//    } else {
//        val newVideo = video.copy(id = generateNewId())
//        _videos.value = _videos.value + newVideo
//    }
//    Log.d("VideoDao", "Video salvo: ${video.url}")
//    Log.d("VideoDao", "Lista de vídeos atualizada: ${_videos.value}")
}

fun Video.toVideoEntity() = VideoEntity(
    id = this.id,
    url = this.url,
    category = this.category
)

fun VideoEntity.toVideo() = Video(
    id = this.id,
    url = this.url,
    category = this.category
)