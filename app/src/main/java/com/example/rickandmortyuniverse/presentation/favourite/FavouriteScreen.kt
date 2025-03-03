package com.example.rickandmortyuniverse.presentation.favourite

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.rickandmortyuniverse.R
import com.example.rickandmortyuniverse.data.mapper.getSeasonNumber
import com.example.rickandmortyuniverse.domain.entity.Episode
import com.example.rickandmortyuniverse.presentation.main.EpisodeCard
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun FavouriteScreen(paddingValues: PaddingValues) {
    val favouriteViewModel: FavouriteEpisodesViewModel = hiltViewModel()
    val screenState = favouriteViewModel.screenState.collectAsState(FavouriteScreenState.Initial)
    val currentState = screenState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background)
    ) {
        FavouriteHeader()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            when (currentState) {
                is FavouriteScreenState.CharactersLoaded -> FavouriteContent(currentState.episodes, favouriteViewModel)
                FavouriteScreenState.Empty -> EmptyFavouriteScreen()
                FavouriteScreenState.Initial -> {}
            }
        }
    }
}

@Composable
fun FavouriteHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Избранные эпизоды",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.6f)
                .height(2.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun FavouriteContent(episodes: List<Episode>, viewModel: FavouriteEpisodesViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { PieChartStats(episodes) }

        items(episodes, key = { it.id }) { episode ->
            EpisodeCard(
                episode = episode,
                onCardLongClickListener = { viewModel.changeFavouriteStatus(episode) },
                onCardClickListener = {},
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun PieChartStats(favouriteEpisodes: List<Episode>) {
    val data = favouriteEpisodes
        .groupBy { it.getSeasonNumber() }
        .map { (season, episodes) ->
            Pie(
                label = "Сезон $season",
                data = episodes.size.toDouble(),
                color = getRandomColor(season),
                selectedColor = getGradientColor(season)
            )
        }

    var selectedIndex by remember { mutableStateOf(-1) }
    val animatedData by rememberUpdatedState(newValue = data)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Статистика по сезонам",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            PieChart(
                modifier = Modifier.size(250.dp),
                data = animatedData.mapIndexed { index, pie ->
                    pie.copy(selected = index == selectedIndex)
                },
                onPieClick = { pie ->
                    selectedIndex = data.indexOf(pie)
                },
                selectedScale = 1.1f,
                scaleAnimEnterSpec = tween(600, easing = EaseOutBounce),
                colorAnimEnterSpec = tween(400),
                colorAnimExitSpec = tween(400),
                style = Pie.Style.Fill
            )
        }
    }
}

@Composable
fun EmptyFavouriteScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_list_animation))
    val progress by animateLottieCompositionAsState(composition)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Список избранных пуст",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

fun getRandomColor(season: Int): Color {
    val colors = listOf(Color.Cyan, Color.Green, Color.Blue, Color.Magenta, Color.Yellow)
    return colors[season % colors.size]
}

fun getGradientColor(season: Int): Color {
    val gradientColors = listOf(Color(0xFF00FFFF), Color(0xFF33FF99), Color(0xFFFF66CC), Color(0xFFFF9900))
    return gradientColors[season % gradientColors.size]
}

