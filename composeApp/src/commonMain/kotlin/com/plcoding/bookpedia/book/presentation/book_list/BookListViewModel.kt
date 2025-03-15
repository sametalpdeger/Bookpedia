package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookListViewModel(
    private val bookRepository: BookRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            observeSearchQuery()
            if (cachedBooks.isEmpty()) {
                observerSearchParams()
            }
            observeFavoriteBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var cachedBooks: List<Book> = emptyList()
    private var searchJob: Job? = null
    private var favoriteJob: Job? = null

    private fun onLoadMore() {
        _state.update {
            it.copy(
                searchParams = it.searchParams.copy(
                    offset = it.searchParams.offset + 1
                )
            )
        }
    }

    private fun observeSearchQuery() {
        _state
            .map { it.searchParams.query }
            .distinctUntilChanged()
            .debounce(50L)
            .onEach { query ->
                if (query.isBlank()) {
                    _state.update {
                        it.searchResults.addAll(0, cachedBooks)
                        it.copy(
                            errorMessage = null,
                            isLoading = false,
                            isWholeListLoading = false,
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun observerSearchParams() {
        state
            .map { it.searchParams }
            .distinctUntilChanged()
            .debounce(50L)
            .onEach { params ->
                val (query, limit, offset) = params

                when {
                    query.length > 2 -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                isWholeListLoading = offset == 0,
                            )
                        }
                        searchJob?.cancel()
                        searchJob = searchBooks(query, limit, offset)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun observeFavoriteBooks() {
        favoriteJob?.cancel()
        favoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { books ->
                _state.value.favoriteBooks.addAll(books)
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String, limit: Int = 10, offset: Int = 0): Job =
        viewModelScope.launch {
            bookRepository
                .searchBooks(query, limit, offset)
                .onSuccess { books ->
                    _state.update {
                        if (offset != 0) it.searchResults.addAll(books) else {
                            it.searchResults.clear()
                            it.searchResults.addAll(books)
                        }

                        it.copy(
                            isWholeListLoading = false,
                            isLoading = false,
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.searchResults.clear()
                        it.copy(
                            isLoading = false,
                            isWholeListLoading = false,
                            errorMessage = error.toUiText(),
                        )

                    }
                }
        }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.onRefresh -> {
                viewModelScope.launch {
                    val searchParams = _state.value.searchParams
                    if (searchParams.query.length > 2) {
                        searchJob?.cancel()
                        searchJob =
                            searchBooks(searchParams.query, searchParams.limit, searchParams.offset)
                    }
                }
            }

            is BookListAction.onBookClick -> {

            }

            is BookListAction.onLoadMore -> {
                onLoadMore()
            }

            is BookListAction.onSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchParams = it.searchParams.copy(
                            query = action.query,
                            offset = 0,
                        )
                    )
                }
            }

            is BookListAction.onTabChanged -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = action.index,
                    )
                }
            }
        }
    }
}