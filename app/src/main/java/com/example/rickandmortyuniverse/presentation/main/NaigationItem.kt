package com.example.rickandmortyuniverse.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.rickandmortyuniverse.R
import com.example.rickandmortyuniverse.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {

    object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Filled.Home
    )

    object Favourite: NavigationItem(
        screen = Screen.Favourite,
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Filled.Favorite
    )

    object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Filled.Person
    )
}