package org.eaortizm.movies.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.eaortizm.movies.data.Movie
import org.eaortizm.movies.ui.screens.Screen
import org.eaortizm.movies.ui.screens.common.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel, onBackClick: () -> Unit ) {
    val state = viewModel.state
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior()

    Screen {
        Scaffold(
            topBar = {
                TopAppBarDetail(
                    title = state.movie?.title ?: "",
                    onBackClick = onBackClick,
                    scrollState = scrollState
                ) 
            }
        ) { padding ->

            LoadingIndicator(state.isLoading, modifier = Modifier.padding(padding))

            state.movie?.let { movie ->
                MovieDetail(movie, modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdropPath ?: movie.posterPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Text(
            text = movie.overview ,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = buildAnnotatedString {
                property("Original language", movie.originalLanguage)
                property("Original title", movie.originalTitle)
                property("Popularity", movie.popularity.toString())
                property("Release date", movie.releaseDate)
                property("Vote average", movie.voteAverage.toString(), endLine = true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),
        )
    }
}

private fun AnnotatedString.Builder.property(name: String, value: String, endLine: Boolean = false ) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)

        if(!endLine){
            append("\n")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarDetail(
    title: String,
    onBackClick: () -> Unit,
    scrollState: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(title ) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollState
    )
}