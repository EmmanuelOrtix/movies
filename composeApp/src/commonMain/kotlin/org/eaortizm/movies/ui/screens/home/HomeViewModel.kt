package org.eaortizm.movies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.eaortizm.movies.data.Movie
import org.eaortizm.movies.data.MovieRepository

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(isLoading = true)
            state = UiState(
                isLoading = false,
                movies = repository.fetchPopularMovies()
            )
        }
    }

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

}
