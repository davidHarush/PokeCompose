package com.david.pokemon.ui.screens.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.ui.screens.details.DetailScreen
import com.david.pokemon.ui.screens.main.MainPagingScreen
import com.david.pokemon.ui.screens.main.MainViewModel
import com.david.pokemon.ui.screens.search.SearchScreen
import com.david.pokemon.ui.screens.search.SearchViewModel


@Composable
fun NavigationGraph(navController: NavHostController,
                    mainViewModel: MainViewModel = hiltViewModel(),
                    searchViewModel: SearchViewModel = hiltViewModel(),
) {

    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            val pokePagingData = mainViewModel.pagingData.collectAsLazyPagingItems()
            MainPagingScreen(navController = navController, pokePagingData)
        }
        composable(Screen.Favorites.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Favorites", fontSize = 24.sp, color = Color.White)
            }
        }

        composable(Screen.Setting.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Setting", fontSize = 24.sp, color = Color.White)
            }
        }

        composable(Screen.Search.route) {
//            SearchScreen(navController = navController,
//                pokeSearchData = viewModel.searchData.collectAsState(initial = emptyArray<PokeCoreDataCharacter>()))

            val pokeSearchData = searchViewModel.searchData.collectAsState(initial = emptyArray<PokeCoreDataCharacter>())
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = pokeSearchData.value.toString(), fontSize = 24.sp, color = Color.White)
            }
        }

        composable(
            route = Screen.Detail.route + "/{name}/{id}", arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    nullable = false
                },
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                })
        )
        { navBackStackEntry ->
            val name: String = navBackStackEntry.arguments?.getString("name") ?: ""
            val id: String = navBackStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(
                pokeCoreData = PokeCoreDataCharacter(
                    id = id, name = name
                )
            )
        }

    }
}
