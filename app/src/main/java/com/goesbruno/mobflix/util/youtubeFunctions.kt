package com.goesbruno.mobflix.util

fun extractYouTubeVideoId(url: String): String? {
    val regex =
        Regex("(?<=v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v=|/watch\\?v=|\\u0026v=)[^#&?\\n]*")
    val matchResult = regex.find(url)
    return matchResult?.value
}

fun isValidYouTubeUrl(url: String?): Boolean {
    if (url != null){
        val youtubeRegex = Regex("^(https?://)?(www\\.)?(youtube\\.com|youtu\\.?be)/.+$")
        return youtubeRegex.matches(url)
    } else {
        return false
    }

}