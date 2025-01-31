package org.eaortizm.movies.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
)