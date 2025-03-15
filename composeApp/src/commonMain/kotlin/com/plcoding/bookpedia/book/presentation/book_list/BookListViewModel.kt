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

// presentation -> domain <- data
@OptIn(FlowPreview::class)
class BookListViewModel(
    private val bookRepository: BookRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observerSearchQuery()
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

    private fun observerSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks,
                            )
                        }
                    }


                    query.length > 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private suspend fun observeFavoriteBooks() {
        favoriteJob?.cancel()
        favoriteJob = bookRepository
            .getFavoriteBooks()
            .onEach { books ->
                _state.update {
                    it.copy(
                        favoriteBooks = books
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
            )
        }

        bookRepository
            .searchBooks(query)
            .onSuccess { books ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResults = books,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText(),
                    )
                }
            }

    }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.onRefresh -> {
                viewModelScope.launch {
                    val query = _state.value.searchQuery
                    if (query.length > 2) {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }

            is BookListAction.onBookClick -> {

            }

            is BookListAction.onSearchQueryChanged -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query,
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