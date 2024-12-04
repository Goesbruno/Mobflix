package com.goesbruno.mobflix.di

import androidx.room.Room
import com.goesbruno.mobflix.data.database.MobflixDatabase
import com.goesbruno.mobflix.data.repository.VideoRepository
import com.goesbruno.mobflix.viewModel.FilteredVideoListScreenViewModel
import com.goesbruno.mobflix.viewModel.HomeScreenViewModel
import com.goesbruno.mobflix.viewModel.VideoFormViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::VideoFormViewModel)
    viewModelOf(::FilteredVideoListScreenViewModel)
}

val storageModule = module {
    singleOf(::VideoRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            MobflixDatabase::class.java, "mobflix.db"
        )
            .build()
    }
    single {
        get<MobflixDatabase>().videoDao()
    }
}