package org.eaortizm.movies.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import movies.composeapp.generated.resources.Res
import movies.composeapp.generated.resources.api_key
import org.eaortizm.movies.data.MovieRepository
import org.eaortizm.movies.data.MovieService
import org.eaortizm.movies.data.movies
import org.eaortizm.movies.ui.screens.detail.DetailScreen
import org.eaortizm.movies.ui.screens.detail.DetailViewModel
import org.eaortizm.movies.ui.screens.home.HomeScreen
import org.eaortizm.movies.ui.screens.home.HomeViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val repository = movieRepository()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onMovieClick = { movieDetail ->
                    navController.navigate("detail/${movieDetail.id}")
                },
                viewModel = viewModel {
                    HomeViewModel(repository)
                }
            )
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            requireNotNull(movieId)
            DetailScreen(
                viewModel = viewModel {DetailViewModel(movieId, repository)},
                onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
private fun movieRepository(
    apiKey: String = stringResource(Res.string.api_key)
): MovieRepository = remember {
    val client =
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", apiKey)
                }
            }
        }
    MovieRepository(MovieService(client))
}