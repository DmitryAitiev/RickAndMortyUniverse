package com.example.rickandmortyuniverse.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rickandmortyuniverse.domain.entity.Episode

@Composable
fun ListEpisodesScreen(
    paddingValues: PaddingValues,
    onCardClickListener: (episode: Episode) -> Unit,
    ) {
    val viewModel: ListEpisodeViewModel = viewModel()
    val screenState = viewModel.screenState.collectAsState(ListEpisodesScreenState.Initial)

    when(val currentState = screenState.value) {
        is ListEpisodesScreenState.EpisodesState -> {
            ListEpisodes(
                paddingValues = paddingValues,
                episodes = currentState.episodes,
                onCardClickListener = onCardClickListener,
                nextDataLoading = currentState.nextDataIsLoading,
                viewModel = viewModel
            )
        }
        ListEpisodesScreenState.Initial -> {}
        ListEpisodesScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}

@Composable
fun ListEpisodes(
    paddingValues: PaddingValues,
    episodes: List<Episode>,
    onCardClickListener: (episode: Episode) -> Unit,
    nextDataLoading: Boolean,
    viewModel: ListEpisodeViewModel
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = episodes, key = {it.id}) {episode ->
            EpisodeCard(
                episode = episode,
                onCardClickListener = {
                    onCardClickListener(episode)
                }
            )
        }
        item {
            if (nextDataLoading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            else {
                SideEffect {
                    viewModel.loadNextEpisodes()
                }
            }
        }
    }
}