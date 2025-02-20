package com.example.rickandmortyuniverse.presentation.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmortyuniverse.R
import com.example.rickandmortyuniverse.domain.entity.Episode

@Composable
fun ListEpisodesScreen(
    paddingValues: PaddingValues,
    onCardClickListener: (episode: Episode) -> Unit,
) {
    val viewModel: ListEpisodeViewModel = hiltViewModel()
    val screenState by viewModel.screenState.collectAsState(ListEpisodesScreenState.Initial)
    Log.d("ListEpisodesScreen", "ScreenState: $screenState")

    if (screenState != ListEpisodesScreenState.Initial) {
        BottomSheetContent(
            paddingValues = paddingValues,
            onCardClickListener = onCardClickListener,
            viewModel = viewModel,
            state = screenState
        )
    }
}

@Composable
fun ListEpisodes(
    paddingValues: PaddingValues,
    onCardClickListener: (episode: Episode) -> Unit,
    viewModel: ListEpisodeViewModel
) {
    val pagingItems = viewModel.episodesPagingFlow.collectAsLazyPagingItems()
    LazyColumn(
        modifier = Modifier
                .padding(bottom = 80.dp),
        contentPadding = PaddingValues(
            top = 0.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = pagingItems.itemCount, key = { index ->
            pagingItems[index]?.id ?: index
        }) { index ->
            val episode = pagingItems[index]
            if (episode != null) {
                EpisodeCard(
                    episode = episode,
                    onCardClickListener = {
                        onCardClickListener(episode)
                    }
                )
            }
        }
        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    paddingValues: PaddingValues,
    onCardClickListener: (episode: Episode) -> Unit,
    viewModel: ListEpisodeViewModel,
    state: ListEpisodesScreenState
) {
    val sheetState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            confirmValueChange = {
                it != SheetValue.Hidden
            }
        )
    )
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val peekHeight = screenHeight * 0.5f

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = peekHeight,
        sheetContent = {
            when(state) {
                is ListEpisodesScreenState.EpisodesState ->
                    ListEpisodes(
                    paddingValues = paddingValues,
                    onCardClickListener = onCardClickListener,
                    viewModel = viewModel,
                )
                is ListEpisodesScreenState.Loading -> {}
                is ListEpisodesScreenState.Initial -> {}
            }
        },
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.main_photo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .blur(10.dp),
                contentScale = ContentScale.FillBounds
            )
            Image(
                painter = painterResource(R.drawable.main_title),
                contentDescription = null
            )
        }
    }
}