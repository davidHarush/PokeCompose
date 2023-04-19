package com.david.pokemon.ui.screens.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.david.network.IPokeService
import com.david.pokemon.BaseViewModel
import com.david.pokemon.UiState
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokePagingRepo
import com.david.pokemon.dommain.PokemonRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val pokemonRepo: PokemonRepo) : BaseViewModel() {

    var pokeListState = MutableStateFlow<UiState<ArrayList<PokeCoreDataCharacter>>>(UiState.Loading)
        private set

    var pagingData: Flow<PagingData<PokeCoreDataCharacter>> = MutableStateFlow(
        PagingData.from(
            emptyList()
        )
    )
        private set

    private var pokePagingRepo: PokePagingRepo = PokePagingRepo()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            patchPokeList()
        }
    }

    private suspend fun patchPokeList() {
        pagingData = pokePagingRepo.getPokeList(
            pokemonRepo = pokemonRepo
        )
            .cachedIn(viewModelScope)
    }

    private suspend fun renderData(data: Flow<ArrayList<PokeCoreDataCharacter>>) {
        data.catch { exception ->
            pokeListState.emit(UiState.Error(exception))
        }
            .collect { pokeList ->
                pokeListState.emit(UiState.Success(pokeList))
            }
    }

}