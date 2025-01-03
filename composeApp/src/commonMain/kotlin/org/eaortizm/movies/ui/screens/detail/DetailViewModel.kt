package org.eaortizm.movies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.eaortizm.movies.data.Movie
import org.eaortizm.movies.data.MovieRepository

class DetailViewModel(
    private val id: Int,
    private val repository: MovieRepository
): ViewModel() {

    var state by mutableStateOf(UiState())

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state = UiState(isLoading = false, movie = repository.fetchMovieById(id))
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val movie: Movie? = null,
    )
}
