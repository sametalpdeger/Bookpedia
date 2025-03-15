package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDTO
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
        offset: Int? = null,
    ): Result<SearchResponseDTO, DataError.Remote>

    suspend fun getBookDetails(bookID: String): Result<BookWorkDto, DataError.Remote>
}