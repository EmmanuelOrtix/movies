package org.eaortizm.movies.data

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
)

val movies = (1 ..100).map {
    Movie(
        id = it,
        title = "Movie $it",
        posterPath = "https://picsum.photos/200/300?id=$it"

    )
}