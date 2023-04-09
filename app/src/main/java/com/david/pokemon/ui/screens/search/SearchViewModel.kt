package com.david.pokemon.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokemonCachedRepoImpl
import com.david.pokemon.runDefaultCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pokemonRepoImpl: PokemonCachedRepoImpl,
) :
    ViewModel() {

    var searchData: Flow<ArrayList<PokeCoreDataCharacter>> = MutableStateFlow(
        arrayListOf()
    )
        private set


     fun searchPoke(searchTerm : String) {
         runDefaultCoroutine {
             Log.i("dddddyyyy","2-> "+searchTerm)
             pokemonRepoImpl.log()
             searchData =
                 pokemonRepoImpl.searchPokemon(searchTerm)
             pokemonRepoImpl.log()
         }
     }

}