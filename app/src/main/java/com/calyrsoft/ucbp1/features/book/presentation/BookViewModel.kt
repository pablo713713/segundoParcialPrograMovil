package com.calyrsoft.ucbp1.features.book.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.book.domain.error.Failure
import com.calyrsoft.ucbp1.features.book.domain.model.BookModel
import com.calyrsoft.ucbp1.features.book.domain.usecase.FindByBookNameUseCase
import com.calyrsoft.ucbp1.features.book.presentation.error.ErrorMessageProvider
import com.calyrsoft.ucbp1.features.book.data.datasource.BookLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(
    private val usecase: FindByBookNameUseCase,
    private val context: Context,
    private val local: BookLocalDataSource
) : ViewModel() {

    sealed class BookStateUI {
        object Init : BookStateUI()
        object Loading : BookStateUI()
        data class Error(val message: String) : BookStateUI()
        data class Success(val books: List<BookModel>) : BookStateUI()
    }

    private val _state = MutableStateFlow<BookStateUI>(BookStateUI.Init)
    val state: StateFlow<BookStateUI> = _state.asStateFlow()

    // 👇 exposición de “me gusta” (más nuevos → más antiguos)
    val liked: StateFlow<List<BookModel>> =
        local.observeLiked().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun searchBooks(query: String) {
        val errorMessageProvider = ErrorMessageProvider(context)
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = BookStateUI.Loading
            val result = usecase.invoke(query)
            result.fold(
                onSuccess = { list -> _state.value = BookStateUI.Success(list) },
                onFailure = { throwable ->
                    val message = errorMessageProvider.getMessage(
                        (throwable as? Failure) ?: Failure.Unknown(throwable)
                    )
                    _state.value = BookStateUI.Error(message)
                }
            )
        }
    }

    // 👇 guardar “me gusta”
    fun likeBook(book: BookModel) = viewModelScope.launch(Dispatchers.IO) {
        local.like(book) // IGNORE si ya existe (índice único)
    }
}
