package org.eaortizm.movies.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieService(
    private val httpClient: HttpClient
) {
    suspend fun fetchPopularMovies(): RemoteResult {
        return httpClient
            .get("/3/discover/movie?sort_by=popularity.desc")
            .body<RemoteResult>()
    }

    suspend fun fetchMovieById(movieId: Int): RemoteMovie {
        return httpClient
            .get("/3/movie/$movieId")
            .body<RemoteMovie>()

    }
}