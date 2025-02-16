package com.example.rickandmortyuniverse.presentation.main

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmortyuniverse.navigation.AppNavGraph
import com.example.rickandmortyuniverse.navigation.rememberNavigationState
import com.example.rickandmortyuniverse.presentation.characters.CharactersScreen

@Composable
fun MainScreen() {

    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            profileScreenContent = { Text("В разработке") },
            favouriteScreenContent = {Text("В разработке")},
            charactersScreenContent = {episodeId ->
                CharactersScreen(
                    onBackPressed = {navigationState.navHostController.popBackStack()},
                    episodeId = episodeId
                )
            },
            episodesListScreenContent = {
                ListEpisodesScreen(
                    paddingValues = it,
                    onCardClickListener = {navigationState.navigateToCharacter(episodeId = it.id)}
                )
            }
        )
    }
}