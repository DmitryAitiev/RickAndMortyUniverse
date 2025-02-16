package com.example.rickandmortyuniverse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rickandmortyuniverse.domain.entity.Character
import com.example.rickandmortyuniverse.domain.entity.Episode

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    profileScreenContent: @Composable () -> Unit,
    favouriteScreenContent: @Composable () -> Unit,
    episodesListScreenContent: @Composable () -> Unit,
    charactersScreenContent: @Composable (Int) -> Unit
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        homeScreenNavGraph (
            episodesListScreenContent = episodesListScreenContent,
            charactersScreenContent = charactersScreenContent
        )
        composable(Screen.Favourite.route) {
            favouriteScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
    }
}