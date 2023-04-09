package com.david.pokemon.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokemonCachedRepoImpl
import com.david.pokemon.dommain.paging.PokePagingRepo
import com.david.pokemon.runIoCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokemonRepoImpl: PokemonCachedRepoImpl,
    private var pokePagingRepo: PokePagingRepo,
) :
    ViewModel() {

    var pagingData: Flow<PagingData<PokeCoreDataCharacter>> = MutableStateFlow(
        PagingData.from(
            emptyList()
        )
    )
        private set

    init {
        runIoCoroutine {
            patchPokeList()
        }
    }


    private suspend fun patchPokeList() {
        pagingData = pokePagingRepo.getPokeList(
            pokeRepo = pokemonRepoImpl
        )
            .cachedIn(viewModelScope)
    }


}