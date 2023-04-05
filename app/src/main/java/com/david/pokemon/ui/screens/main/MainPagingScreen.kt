package com.david.pokemon.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberAsyncImagePainter
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.getImage
import com.david.pokemon.ui.screens.details.DetailText
import com.david.pokemon.ui.screens.navigation.Screen
import com.david.pokemon.ui.theme.DarkBlue
import com.david.pokemon.ui.theme.DeepBlue


@Composable
fun MainPagingScreen(
    navController: NavHostController,
    pokePagingData: LazyPagingItems<PokeCoreDataCharacter>,
    viewModel: MainViewModel = hiltViewModel(),
) {

    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Paging(pokePagingData, navController, viewModel)
    }

}

@Composable
fun Paging(
    pokePagingData: LazyPagingItems<PokeCoreDataCharacter>,
    navController: NavHostController, viewModel: MainViewModel,
) {

    when (pokePagingData.loadState.refresh) {
        LoadState.Loading -> {
            //TODO implement loading state
        }
        is LoadState.Error -> {
            //TODO implement error state
        }
        else -> {
            LazyColumn {
                itemsIndexed(pokePagingData) { _, item ->
                    CharacterItem(
                        character = item,
                        navController = navController,
                        viewModel
                    )
                }
            }
        }

    }
}

@Composable
fun CharacterItem(
    character: PokeCoreDataCharacter?,
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    if (character == null) {
        return DetailText(text = "empty")
    }

    Card(
        backgroundColor = DarkBlue,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screen.Detail.route + "/${character.name}/${character.id}")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                PokeImage(character)
//                PokedexEntry(character.getImage(), viewModel = viewModel)
                Spacer(modifier = Modifier.width(24.dp))
                Box {
                    Text(
                        text = character.name,
                        fontSize = 22.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun PokeImage(character: PokeCoreDataCharacter) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(character.getImage()),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}
