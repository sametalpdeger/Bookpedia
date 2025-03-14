package com.plcoding.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import org.koin.core.module.dsl.viewModelOf
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.book.presentation.book_list.SharedSelectedBookViewModel
import com.plcoding.bookpedia.book_detail.BookDetailViewModel
import org.koin.core.module.Module
import com.plcoding.bookpedia.book.data.database.DatabaseFactory
import com.plcoding.bookpedia.book.data.database.FavoriteBookDatabase

expect val platformModule : Module

val sharedModule = module {
    single {
        HttpClientFactory.create(get())
    }

    single {
         get<DatabaseFactory>()
             .create()
             .setDriver(BundledSQLiteDriver())
             .build()
    }

    single {
        get<FavoriteBookDatabase>().favoriteBookDao
    }

    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SharedSelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}