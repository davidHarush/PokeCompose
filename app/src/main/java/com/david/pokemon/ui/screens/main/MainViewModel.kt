package com.david.pokemon.ui.screens.main

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.palette.graphics.Palette
import com.david.pokemon.BaseViewModel
import com.david.pokemon.UiState
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokePagingRepo
import com.david.pokemon.dommain.PokemonRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val pokemonRepo: PokemonRepo  ) : BaseViewModel() {

    var pokeListState = MutableStateFlow<UiState<ArrayList<PokeCoreDataCharacter>>>(UiState.Loading)
        private set

    var pagingData: Flow<PagingData<PokeCoreDataCharacter>> = MutableStateFlow(PagingData.from(
        emptyList()// arrayListOf()
    ))
        private set

    private var pokePagingRepo : PokePagingRepo = PokePagingRepo()


    init {
        viewModelScope.launch (Dispatchers.IO) {
//            pokemonRepository.initData()
//            delay(2000)
            getPagingPokeList()
        }
//        getData()
    }

    suspend fun getPagingPokeList() {

        pagingData = pokePagingRepo.getPokeList(viewModelScope = viewModelScope,
            pokemonRepo = pokemonRepo
        )
            .cachedIn(viewModelScope)
    }



//    private fun getPagingPokeList() {
//        runIoCoroutine {
//           val data =  pokePagingRepo.getPokeList(viewModelScope, pokemonRepository )
//            pagingData = pokePagingRepo.getPokeList(viewModelScope, pokemonRepository )
//
//            pagingData.
////            pagingData.collect{
////               Log.i("ddddd", it.toString())
////            }//            Log.i("dddddd", "getPagingPokeList ")
////            pagingData = pokemonRepository.getPokemonList(viewModelScope).cachedIn(viewModelScope)
////            pagingData.collect{
////               Log.i("ddddd", it.toString())
////            }
//        }
//    }


//    private fun getData() {
//        runIoCoroutine {
//            pokeListState.emit(UiState.Loading)
//            renderData(pokemonRepository.getPokemonList())
//        }
//    }
    private suspend fun renderData(data: Flow<ArrayList<PokeCoreDataCharacter>>) {


        Log.i("dddxxx","renderData")

        data.catch { exception ->
            pokeListState.emit(UiState.Error(exception))
        }
            .collect { pokeList ->
                pokeListState.emit(UiState.Success(pokeList))
            }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }


}