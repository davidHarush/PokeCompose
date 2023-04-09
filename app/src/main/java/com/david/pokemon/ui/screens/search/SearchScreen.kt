package com.david.pokemon.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.ui.screens.main.MainViewModel
import com.david.pokemon.ui.screens.main.Paging
import com.david.pokemon.ui.theme.DeepBlue

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    pokeSearchData: State<ArrayList<PokeCoreDataCharacter>>,
) {

    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
      //  Paging(pokePagingData, navController, viewModel)
    }

}