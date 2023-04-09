package com.david.pokemon.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.david.pokemon.UiState
import com.david.pokemon.dommain.PokeCharacter
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokemonRepoImpl
import com.david.pokemon.runIoCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pokemonRepoImpl: PokemonRepoImpl,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val name: String = checkNotNull(savedStateHandle["name"])

    private val id: String = checkNotNull(savedStateHandle["id"])

    init {
        val poke = PokeCoreDataCharacter(id = id, name = name)
        getPokemon(poke)
    }

    var pokeState = MutableStateFlow<UiState<PokeCharacter>>(UiState.Loading)
        private set

    private fun getPokemon(pokeCoreDataCharacter: PokeCoreDataCharacter) {
        runIoCoroutine {
            pokeState.emit(UiState.Loading)
            renderData(pokemonRepoImpl.getPokemon(pokeCoreDataCharacter))
        }
    }

    private suspend fun renderData(data: Flow<PokeCharacter>) {
        data.catch { exception ->
            pokeState.emit(UiState.Error(exception))
        }
            .collect { poke ->
                pokeState.emit(UiState.Success(poke))
            }
    }
}
