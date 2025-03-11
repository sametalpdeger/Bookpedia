import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreen
import com.plcoding.bookpedia.book.presentation.book_list.BookListState

@Preview
@Composable
fun BookListScreenPreview() {
    BookListScreen(
        onAction = {},
        state = BookListState(
            searchQuery = "Kotlin!",
            searchResults = emptyList(),
            favoriteBooks = emptyList(),
            isLoading = false,
            errorMessage = null,
            selectedTabIndex = 0,
        )
    )
}