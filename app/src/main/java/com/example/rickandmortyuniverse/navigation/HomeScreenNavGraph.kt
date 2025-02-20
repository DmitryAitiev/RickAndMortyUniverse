package com.example.rickandmortyuniverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.rickandmortyuniverse.domain.entity.Episode

fun NavGraphBuilder.homeScreenNavGraph(
    episodesListScreenContent: @Composable () -> Unit,
    charactersScreenContent: @Composable (Int) -> Unit
) {
    navigation(
        startDestination = Screen.ROUTE_EPISODE_LIST,
        route = Screen.Home.route
    ) {
        composable(
            Screen.ROUTE_EPISODE_LIST
        ) {
            episodesListScreenContent()
        }
        composable (
            route = Screen.CharacterList.route,
            arguments = listOf(
                navArgument(Screen.KEY_EPISODE_CARD) {
                    type = NavType.IntType
                }
            )
        ) {
            val episodeId = it.arguments?.getInt(Screen.KEY_EPISODE_CARD) ?: throw RuntimeException("Args is null")
            charactersScreenContent(episodeId)       }
    }
}