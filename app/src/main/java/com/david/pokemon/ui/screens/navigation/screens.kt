package com.david.pokemon.ui.screens.navigation

import androidx.compose.ui.graphics.Color
import com.david.pokemon.R
import com.david.pokemon.ui.theme.TabsIconColor


sealed class Screen(var title: String, var icon: Int, var route: String, var iconColor: Color) {

    companion object {
        fun isNeedToHideTabs(route: String): Boolean  =
            route.startsWith(Detail.route) || route.startsWith(Search.route)

        fun isNeedToShowSearchBar(route: String): Boolean  =
            route.startsWith(Main.route) || route.startsWith(Search.route)

    }
    object Main : Screen(
        route = "main_screen",
        title = "Main",
        icon = R.drawable.ic_home,
        iconColor = TabsIconColor.main

    )
    object Detail : Screen(
        route = "detail_screen",
        title = "Details",
        icon = R.drawable.ic_info,
        iconColor = TabsIconColor.main

    )
    object Setting : Screen(
        route = "setting_screen",
        title = "Setting",
        icon = R.drawable.ic_settings,
        iconColor = TabsIconColor.settings
    )

    object Favorites : Screen(
        route = "favorites_screen",
        title = "Favorites",
        icon = R.drawable.ic_favorite,
        iconColor = TabsIconColor.favorites
    )
    object Search : Screen(
        route = "Search_screen",
        title = "Search",
        icon = R.drawable.ic_search,
        iconColor = TabsIconColor.search
    )

    fun withArgs(vararg args: String) =
        StringBuilder().apply {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }.toString()

}