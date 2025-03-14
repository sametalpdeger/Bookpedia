package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResponseDTO
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.plcoding.bookpedia.core.domain.Result

private const val BASE_URL = "https://openlibrary.org/search.json"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
):RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
    ): Result<SearchResponseDTO, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = BASE_URL
            ) {
                parameter("q", query)
                parameter("limit",resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,language,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count")
            }
        }
    }
}