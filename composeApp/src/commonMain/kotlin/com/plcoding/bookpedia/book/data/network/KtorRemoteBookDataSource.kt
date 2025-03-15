package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDTO
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
        offset: Int?,
    ): Result<SearchResponseDTO, DataError.Remote> {
        return safeCall<SearchResponseDTO> {
            httpClient.get(
                urlString = "$BASE_URL/search.json",
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("offset", offset)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,language,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
                )
            }
        }
    }

    override suspend fun getBookDetails(bookID: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookID.json"
            )
        }
    }
}