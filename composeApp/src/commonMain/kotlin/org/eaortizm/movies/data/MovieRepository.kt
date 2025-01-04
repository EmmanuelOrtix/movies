package org.eaortizm.movies.data

class MovieRepository(private val movieService: MovieService) {
    suspend fun fetchPopularMovies(): List<Movie> {
        return movieService.fetchPopularMovies().results.map {
            it.toDomainMovie()
        }
    }

    suspend fun fetchMovieById(movieId: Int): Movie {
        return movieService.fetchMovieById(movieId).toDomainMovie()
    }

    private fun RemoteMovie.toDomainMovie() = Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        backdropPath = backdropPath?.let { "https://image.tmdb.org/t/p/w500/$it" },
        posterPath = "https://image.tmdb.org/t/p/w185/$posterPath",
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage
    )
}