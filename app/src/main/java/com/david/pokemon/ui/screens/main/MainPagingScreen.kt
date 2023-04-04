package com.david.pokemon.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.getImage
import com.david.pokemon.ui.screens.details.DetailText
import com.david.pokemon.ui.screens.navigation.Screen
import com.david.pokemon.ui.theme.DarkBlue
import com.david.pokemon.ui.theme.DeepBlue
import com.google.accompanist.coil.CoilImage


@Composable
fun MainPagingScreen(navController: NavHostController, pokePagingData: LazyPagingItems<PokeCoreDataCharacter>, viewModel: MainViewModel = hiltViewModel()) {

    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Paging(pokePagingData, navController, viewModel)
//    StaticList(navController , viewModel)
    }

}
//
//@Composable
//fun StaticList(navController: NavHostController, viewModel: PokemonViewModel) {
//
//    val data = viewModel.pokeListState.collectAsState()
//
//    if (data.value is UiState.Loading) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//
//    if (data.value is UiState.Success) {
//        Log.i("ddddxxxxx", "Success")
//        CharacterList(
//            characters = (data.value as UiState.Success).data,
//            navController,
//            viewModel
//        )
//    }
//}



@Composable
fun Paging(
    pokePagingData: LazyPagingItems<PokeCoreDataCharacter>,
    navController: NavHostController, viewModel: MainViewModel
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
//
//@Composable
//fun Paging(viewModel: PokemonViewModel, navController: NavHostController) {
//
//    val data = viewModel.pagingData.collectAsLazyPagingItems()
//
//    Log.i("ddddddd", "Paging data: $data")
//
//
//    LazyColumn {
//        items(items = data.itemSnapshotList) {
//            CharacterItem(
//                character = it,
//                navController = navController,
//                pokemonViewModel = viewModel
//            )
//        }
//
//
//        when (val state = data.loadState.refresh) { //FIRST LOAD
//            is LoadState.Error -> {
//                //TODO Error Item
//            }
//
//            is LoadState.Loading -> { // Loading UI
//                item {
//                    Column(
//                        modifier = Modifier
//                            .fillParentMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center,
//                    ) {
//                        DetailText(
//                            text = "Loading",
//                            size = 24.sp
//                        )
//                        Spacer(modifier = Modifier.height(12.dp))
//                        CircularProgressIndicator(color = Color.LightGray)
//                    }
//                }
//            }
//            else -> {}
//        }
//
//        when (val state = data.loadState.append) { // Pagination
//            is LoadState.Error -> {
//                //TODO Pagination Error Item
//                //state.error to get error message
//            }
//            is LoadState.Loading -> { // Pagination Loading UI
//                item {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center,
//                    ) {
//                        DetailText(text = "Pagination Loading")
//
//                        CircularProgressIndicator(color = Color.Black)
//                    }
//                }
//            }
//            else -> {}
//        }
//    }
//
//
//}

//@Composable
//fun CharacterList(
//    characters: ArrayList<PokeCoreDataCharacter>,
//    navController: NavHostController,
//    pokemonViewModel: PokemonViewModel
//) {
//    Column {
//        Spacer(modifier = Modifier.height(8.dp))
//        LazyColumn {
//            items(items = characters) { character ->
//                CharacterItem(
//                    character = character,
//                    navController = navController,
//                    pokemonViewModel = pokemonViewModel
//                )
//            }
//        }
//    }
//}


@Composable
fun CharacterItem(
    character: PokeCoreDataCharacter?,
    navController: NavHostController,
    viewModel: MainViewModel
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





@Composable
fun PokedexEntry(
    url: String,
    viewModel: MainViewModel
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )

    ) {
        val co = LocalContext.current
        Column {
            CoilImage(
                request = ImageRequest.Builder(co)
                    .data(url)
                    .target {
                        viewModel.calcDominantColor(it) { color ->
                            dominantColor = color
                        }
                    }
                    .build(),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.scale(0.5f)
                )
            }
        }
    }
}


//
//@Composable
//fun PokedexEntry(
//    character: PokeCoreDataCharacter,
//    navController: NavHostController,
//    pokemonViewModel: PokemonViewModel
//) {
//    val defaultDominantColor = MaterialTheme.colors.surface
//    var dominantColor by remember {
//        mutableStateOf(defaultDominantColor)
//    }
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .shadow(5.dp, RoundedCornerShape(10.dp))
//            .clip(RoundedCornerShape(10.dp))
//            .aspectRatio(1f)
//            .background(
//                Brush.verticalGradient(
//                    listOf(
//                        dominantColor,
//                        defaultDominantColor
//                    )
//                )
//            )
//            .clickable {
////                navController.navigate(
////                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
////                )
//            }
//    ) {
//        Column {
//            CoilImage(
//                request = ImageRequest.Builder(LocalContext.current)
//                    .data(character.getImage())
//                    .target {
//                        pokemonViewModel.calcDominantColor(it) { color ->
//                            dominantColor = color
//                        }
//                    }
//                    .build(),
//                contentDescription = character.name,
//                fadeIn = true,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterHorizontally)
//            ) {
//                CircularProgressIndicator(
//                    color = MaterialTheme.colors.primary,
//                    modifier = Modifier.scale(0.5f)
//                )
//            }
//            Text(
//                text = character.name,
//                fontSize = 20.sp,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}






//@Composable
//fun CharacterImage(character: PokeCoreDataCharacter, pokemonViewModel: PokemonViewModel) {
//
//    val defaultDominantColor = MaterialTheme.colors.surface
//    var dominantColor by remember {
//        mutableStateOf(defaultDominantColor)
//    }
//
//    Box(
//        modifier = Modifier
//            .size(100.dp)
//            .background(dominantColor)
//    ) {
//        CoilImage(
//            request = ImageRequest.Builder(LocalContext.current)
//                .data(character.getImage())
//                .target {
//                    pokemonViewModel.calcDominantColor(it) { color ->
//                        dominantColor = color
//                    }
//                }
//                .build(),
//            contentDescription = character.name,
//            fadeIn = true,
//            modifier = Modifier
//                .size(120.dp)
//        ) {
//            CircularProgressIndicator(
//                color = MaterialTheme.colors.primary,
//                modifier = Modifier.scale(0.5f)
//            )
//        }
//    }
//}
//
