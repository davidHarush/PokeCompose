package com.david.pokemon.ui.screens.details

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.david.pokemon.UiState
import com.david.pokemon.dommain.PokeCharacter
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.Stat
import com.david.pokemon.dommain.getImage
import com.david.pokemon.ui.theme.*
import kotlinx.coroutines.delay


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DetailScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    pokeCoreData: PokeCoreDataCharacter
) {
    val pokeState = detailsViewModel.pokeState.collectAsStateWithLifecycle()
    val imageState =  detailsViewModel.viewState.collectAsStateWithLifecycle()

    ImagePreviewScreen(detailsViewModel)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlue)
            .padding(5.dp)
    )
    {
        Column {

            if (pokeState.value is UiState.Success) {
                val character = (pokeState.value as UiState.Success).data
                HeaderData(pokeCoreData = pokeCoreData)
//                DetailData(character)
                Spacer(modifier = Modifier.width(24.dp))
                StatData((pokeState.value as UiState.Success).data.stats)
                HeartCard()
            } else {
                HeaderData(pokeCoreData = pokeCoreData )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }

}

@Composable
fun HeaderData(pokeCoreData: PokeCoreDataCharacter) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(pokeCoreData.getImage()),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = pokeCoreData.name,
            fontSize = 32.sp,
            color = Color.White
        )
    }
}


@Composable
fun DetailData(character: PokeCharacter) {
    return Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .width(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            DetailCard {
                DetailText(text = "Weight: ${character.weight}")
                DetailText(text = "Height: " + character.height.toString())
                DetailText(text = "Base Experience: " + character.baseExperience)
            }
        }

    }
}

@Composable
fun StatData(listState: List<Stat>) {
    Column(
        modifier = Modifier
            .background(DeepBlue),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            itemsIndexed(items = listState) { i, state ->
                Spacer(modifier = Modifier.height(10.dp))
                StatBar((i * 150).toLong(), state.power.toFloat(), state.name)
            }
        }
    }

}


@Composable
fun StatBar(delay: Long, power: Float, title: String) {
    val statBarWidth = 100.dp
    val relativeValue = (power / 100f) * statBarWidth.value * Density(LocalContext.current).density


    val animateFraction = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        delay(delay)
        animateFraction.animateTo(
            targetValue = relativeValue,
            animationSpec = tween(durationMillis = 700)
        )
    }

    DetailCard {
        DetailText(
            text = "$title: ${power.toInt()}",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .height(8.dp)
                .width(statBarWidth)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val borderWidth = 1.dp.toPx()
                val halfBorderWidth = borderWidth / 2
                val rectWidth = width - borderWidth
                val rectHeight = height - borderWidth
                val cornerRadius = CornerRadius(10f, 10f)

                drawRoundRect(
                    color = colorMapper(title), // Fill
                    topLeft = Offset(halfBorderWidth, halfBorderWidth),
                    size = Size(animateFraction.value, rectHeight),
                    cornerRadius = cornerRadius
                )
            }
        }
    }
}

fun colorMapper(statName: String) =
    when (statName) {
        "hp" -> PokeStatColor.hp
        "attack" -> PokeStatColor.attack
        "defense" -> PokeStatColor.defense
        "special-attack" -> PokeStatColor.specialAttack
        "special-defense" -> PokeStatColor.specialDefense
        "speed" -> PokeStatColor.speed
        else -> {
            PokeStatColor.speed
        }
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailCard(
    content: @Composable ColumnScope.() -> Unit
) {
    return AnimatedContent(targetState = false) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(DarkBlue)
                    .padding(10.dp)
            ) {
                content()
            }
        }
    }
}


@Composable
fun DetailText(text: String, size: TextUnit = 16.sp) = Text(
    text = text,
    fontSize = size,
    color = Color.LightGray
)


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeartCard() {
    return AnimatedContent(targetState = false) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .width(150.dp)
                .height(120.dp)
                .padding(5.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                DetailCard {
                    DetailText(text = "Favorites: ", size = 20.sp)
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(110.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        HeartImage()
                    }
                }
            }
        }
    }
}

@Composable
fun HeartImage() {
    var isLiked by remember { mutableStateOf(false) }


    val tint by animateColorAsState(
        targetValue = if (isLiked) Color.Red else Color.Gray,
        animationSpec = tween(durationMillis = 100)
    )
    val animatedSize = animateDpAsState(
        if (isLiked) 80.dp else 40.dp,
        tween(durationMillis = 100, easing = LinearEasing),
        finishedListener = { dp ->
        }
    )

    Crossfade(targetState = isLiked) { liked ->
        val image = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        Icon(
            imageVector = image,
            contentDescription = "Heart",
            tint = tint,
            modifier = Modifier
                .size(animatedSize.value)
                .clickable(onClick = { isLiked = !isLiked })
        )
    }
}

@Composable
private fun ImagePreviewScreen(detailsViewModel: DetailsViewModel) {

    val viewState =  detailsViewModel.viewState.collectAsStateWithLifecycle()

    fun Palette.Swatch.rgbAsColor(): Color = Color(rgb)

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = viewState.value.colorPalette) {
        val systemStatusBarColor = viewState.value.colorPalette?.darkVibrantSwatch?.rgbAsColor() ?: Color.White
        systemUiController.setSystemBarsColor(systemStatusBarColor)
    }

}


//
//
//@Composable
//fun NetworkImage(character: PokeCoreDataCharacter) {
//    val dominantColor = MutableLiveData<Color>()
//
//    LaunchedEffect(character.getImage()) {
//        withContext(Dispatchers.IO) {
//            val inputStream = URL(character.getImage()).openStream()
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            val colors = mutableListOf<Int>()
//            for (x in 0 until bitmap.width) {
//                for (y in 0 until bitmap.height) {
//                    val pixel = bitmap.getPixel(x, y)
//                    colors.add(pixel)
//                }
//            }
//            val mostCommonColor = colors.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: 0
//            val color = Color(mostCommonColor)
//            dominantColor.postValue(color)
//        }
//    }
//
//    val color  = rememberUpdatedState(dominantColor.value)
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(250.dp).background(color.value ?: DeepBlue)
//    ) {
//        Image(
//            painter = rememberImagePainter(
//                data = character.getImage(),
//                builder = {
//                    crossfade(true)
//                }
//            ),
//            contentDescription = null,
//            modifier = Modifier.size(250.dp),
//        )
//    }
//}
