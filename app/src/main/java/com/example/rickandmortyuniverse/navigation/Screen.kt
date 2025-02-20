package com.example.rickandmortyuniverse.navigation

import com.example.rickandmortyuniverse.domain.entity.Episode

sealed class Screen(val route: String) {

    data object Home: Screen(ROUTE_HOME)
    data object Favourite: Screen(ROUTE_FAVOURITE)
    data object Profile: Screen(ROUTE_PROFILE)
    data object CharacterList: Screen(ROUTE_CHARACTER) {

        private const val ROUTE_FOR_ARGS = "character"

        fun getRouteWithArgs(episodeId: Int): String {
            return "$ROUTE_FOR_ARGS/${episodeId}"
        }
    }

    companion object {
        const val KEY_EPISODE_CARD = "episodeId"

        const val ROUTE_EPISODE_LIST = "episode_list"
        const val ROUTE_HOME = "home"
        const val ROUTE_CHARACTER = "character/{$KEY_EPISODE_CARD}"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}