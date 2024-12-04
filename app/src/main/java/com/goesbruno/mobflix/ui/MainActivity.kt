package com.goesbruno.mobflix.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.goesbruno.mobflix.ui.screen.FilteredVideoListScreen
import com.goesbruno.mobflix.ui.screen.HomeScreen
import com.goesbruno.mobflix.ui.screen.VideoFormScreen
import com.goesbruno.mobflix.ui.state.HomeScreenUIState
import com.goesbruno.mobflix.ui.theme.MobflixTheme
import com.goesbruno.mobflix.util.NavRoutes
import com.goesbruno.mobflix.util.makeToast
import com.goesbruno.mobflix.viewModel.FilteredVideoListScreenViewModel
import com.goesbruno.mobflix.viewModel.HomeScreenViewModel
import com.goesbruno.mobflix.viewModel.VideoFormViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobflixTheme {
                val navController = rememberNavController()
                App {

                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.HOME_SCREEN
                    ) {
                        composable(NavRoutes.HOME_SCREEN) {
                            val homeScreenViewModel: HomeScreenViewModel by viewModel()
                            HomeScreen(
                                viewModel = homeScreenViewModel,
                                onVideoLongClick = { video ->
                                    navController.navigate("${NavRoutes.VIDEO_FORM_SCREEN}/${video.id}")
                                },
                                onYoutubeCall = { video ->
                                    lifecycleScope.launch {
                                        homeScreenViewModel.openYoutubeVideo(video.url)
                                        homeScreenViewModel.youtubeIntent.collect { intent ->
                                            startActivity(intent)
                                        }
                                    }
                                },
                                onFabClick = {
                                    navController.navigate("${NavRoutes.VIDEO_FORM_SCREEN}/new")
                                },
                                onTagClick = { category ->
                                    navController.navigate("${NavRoutes.FILTERED_VIDEO_LIST_SCREEN}/${category.name}")
                                }
                            )
                        }
                        composable(
                            route = "${NavRoutes.VIDEO_FORM_SCREEN}/{videoId}",
                            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
                        ) {
                            val videoId = it.arguments?.getString("videoId") ?: ""
                            val videoFormViewModel: VideoFormViewModel by viewModel()

                            LaunchedEffect(videoId) { videoFormViewModel.updateVideoId(videoId) }
                            VideoFormScreen(
                                viewModel = videoFormViewModel,
                                onSaveClick = { urlError, categoryError ->
                                    val context = this@MainActivity
                                    when {
                                        urlError != null -> makeToast(context, urlError)
                                        categoryError != null -> makeToast(context, categoryError)
                                        else -> navController.navigate(NavRoutes.HOME_SCREEN)
                                    }
                                },
                                onDeleteClick = {
                                    navController.navigate(NavRoutes.HOME_SCREEN)
                                }
                            )
                        }
                        composable(
                            route = "${NavRoutes.FILTERED_VIDEO_LIST_SCREEN}/{categoryName}",
                            arguments = listOf(navArgument("categoryName") { type = NavType.StringType }
                            )
                        ) {
                            val categoryName = it.arguments?.getString("categoryName") ?: ""
                            val filteredVideoListViewModel: FilteredVideoListScreenViewModel by viewModel()
                            LaunchedEffect(categoryName) {
                                filteredVideoListViewModel.fetchCategory(categoryName)
                            }
                            FilteredVideoListScreen(
                                viewModel = filteredVideoListViewModel,
                                onVideoLongClick = { video ->
                                    navController.navigate("${NavRoutes.VIDEO_FORM_SCREEN}/${video.id}")
                                },
                                onYoutubeCall = { video ->
                                    lifecycleScope.launch {
                                        filteredVideoListViewModel.openYoutubeVideo(video.url)
                                        filteredVideoListViewModel.youtubeIntent.collect { intent ->
                                            startActivity(intent)
                                        }
                                    }
                                },
                                onTagClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                    }
                }
            }
        }
    }

}


@Composable
fun App(
    content: @Composable () -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    App {
        HomeScreen(HomeScreenUIState())
    }
}