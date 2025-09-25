package com.calyrsoft.ucbp1.di

import com.calyrsoft.ucbp1.R
import com.calyrsoft.ucbp1.features.dollar.data.database.AppRoomDatabase
import com.calyrsoft.ucbp1.features.dollar.data.datasource.DollarLocalDataSource
import com.calyrsoft.ucbp1.features.dollar.data.datasource.RealTimeRemoteDataSource
import com.calyrsoft.ucbp1.features.dollar.data.repository.DollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.repository.IDollarRepository
import com.calyrsoft.ucbp1.features.dollar.domain.usecase.FetchDollarUseCase
import com.calyrsoft.ucbp1.features.dollar.presentation.DollarViewModel

import com.calyrsoft.ucbp1.features.github.data.api.GithubService
import com.calyrsoft.ucbp1.features.github.data.datasource.GithubRemoteDataSource
import com.calyrsoft.ucbp1.features.github.data.repository.GithubRepository
import com.calyrsoft.ucbp1.features.github.domain.repository.IGithubRepository
import com.calyrsoft.ucbp1.features.github.domain.usecase.FindByNickNameUseCase
import com.calyrsoft.ucbp1.features.github.presentation.GithubViewModel

import com.calyrsoft.ucbp1.features.movie.data.api.MovieService
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieRemoteDataSource
import com.calyrsoft.ucbp1.features.movie.data.datasource.MovieLocalDataSource
import com.calyrsoft.ucbp1.features.movie.data.repository.MovieRepository
import com.calyrsoft.ucbp1.features.movie.domain.repository.IMoviesRepository
import com.calyrsoft.ucbp1.features.movie.domain.usecase.FetchPopularMoviesUseCase
import com.calyrsoft.ucbp1.features.movie.presentation.PopularMoviesViewModel
import com.calyrsoft.ucbp1.features.movie.data.database.dao.IMovieDao

import com.calyrsoft.ucbp1.features.profile.application.ProfileViewModel
import com.calyrsoft.ucbp1.features.profile.data.repository.ProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.repository.IProfileRepository
import com.calyrsoft.ucbp1.features.profile.domain.usecase.GetProfileUseCase

// BOOK (OpenLibrary)
import com.calyrsoft.ucbp1.features.book.data.api.BookService
import com.calyrsoft.ucbp1.features.book.data.datasource.BookRemoteDataSource
import com.calyrsoft.ucbp1.features.book.data.repository.BookRepository
import com.calyrsoft.ucbp1.features.book.domain.repository.IBookRepository
import com.calyrsoft.ucbp1.features.book.domain.usecase.FindByBookNameUseCase
import com.calyrsoft.ucbp1.features.book.presentation.BookViewModel
import com.calyrsoft.ucbp1.features.book.data.database.dao.IBookDao
import com.calyrsoft.ucbp1.features.book.data.datasource.BookLocalDataSource

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConstants {
    const val RETROFIT_GITHUB = "RetrofitGithub"
    const val GITHUB_BASE_URL = "https://api.github.com/"

    const val RETROFIT_MOVIE = "RetrofitMovie"
    const val MOVIE_BASE_URL = "https://api.themoviedb.org/"

    // OpenLibrary
    const val RETROFIT_BOOK = "RetrofitOpenLibrary"
    const val OPENLIB_BASE_URL = "https://openlibrary.org/"
}

val appModule = module {

    // OkHttpClient
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit GitHub
    single(named(NetworkConstants.RETROFIT_GITHUB)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.GITHUB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit Movie
    single(named(NetworkConstants.RETROFIT_MOVIE)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.MOVIE_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Retrofit OpenLibrary (Books)
    single(named(NetworkConstants.RETROFIT_BOOK)) {
        Retrofit.Builder()
            .baseUrl(NetworkConstants.OPENLIB_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ===== GitHub =====
    single<GithubService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_GITHUB)).create(GithubService::class.java)
    }
    single { GithubRemoteDataSource(get()) }
    single<IGithubRepository> { GithubRepository(get()) }
    factory { FindByNickNameUseCase(get()) }
    viewModel { GithubViewModel(get(), get()) }

    // ===== Profile =====
    single<IProfileRepository> { ProfileRepository() }
    factory { GetProfileUseCase(get()) }
    viewModel { ProfileViewModel(get()) }

    // ===== Room DB =====
    single { AppRoomDatabase.getDatabase(get()) }

    // Dollar DAOs / DS / Repo / UseCase / VM
    single { get<AppRoomDatabase>().dollarDao() }
    single { RealTimeRemoteDataSource() }
    single { DollarLocalDataSource(get()) }
    single<IDollarRepository> { DollarRepository(get(), get()) }
    factory { FetchDollarUseCase(get()) }
    viewModel { DollarViewModel(fetchDollarUseCase = get(), localDataSource = get()) }

    // Movie DAOs / DS / Repo / UseCase / VM
    single<IMovieDao> { get<AppRoomDatabase>().movieDao() }      // DAO
    single { MovieLocalDataSource(get()) }                       // Local DS
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single<MovieService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_MOVIE)).create(MovieService::class.java)
    }
    single { MovieRemoteDataSource(get(), get(named("apiKey"))) } // Remote DS
    single<IMoviesRepository> { MovieRepository(get(), get()) }   // Repo (remote + local)
    factory { FetchPopularMoviesUseCase(get()) }
    viewModel { PopularMoviesViewModel(get()) }

    // Book DAOs / DS / Repo / UseCase / VM
    single<IBookDao> { get<AppRoomDatabase>().bookDao() }
    single { BookLocalDataSource(get()) }
    single<BookService> {
        get<Retrofit>(named(NetworkConstants.RETROFIT_BOOK)).create(BookService::class.java)
    }
    single { BookRemoteDataSource(get()) }
    single<IBookRepository> { BookRepository(get()) }
    factory { FindByBookNameUseCase(get()) }
    viewModel { BookViewModel(get(), get(), get()) }
}
