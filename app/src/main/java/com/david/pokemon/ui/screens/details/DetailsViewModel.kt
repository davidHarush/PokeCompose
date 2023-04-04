package com.david.pokemon.ui.screens.details

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.david.pokemon.BaseViewModel
import com.david.pokemon.UiState
import com.david.pokemon.dommain.PokeCharacter
import com.david.pokemon.dommain.PokeCoreDataCharacter
import com.david.pokemon.dommain.PokemonRepo
import com.david.pokemon.dommain.getImage
import com.david.pokemon.runIoCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pokemonRepo: PokemonRepo,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val name: String = checkNotNull(savedStateHandle["name"])

    private val id: String = checkNotNull(savedStateHandle["id"])

    init {
        val  poke =PokeCoreDataCharacter(id = id, name = name)
        getPokemon(poke)
        setImageUri(poke.getImage().toUri())
    }

    var pokeState = MutableStateFlow<UiState<PokeCharacter>>(UiState.Loading)
        private set

    private fun getPokemon(pokeCoreDataCharacter: PokeCoreDataCharacter) {
        runIoCoroutine {
            pokeState.emit(UiState.Loading)
            renderData(pokemonRepo.getPokemon(pokeCoreDataCharacter))
        }
    }

    private suspend fun renderData(data: Flow<PokeCharacter>) {
        data.catch { exception ->
            pokeState.emit(UiState.Error(exception))
        }
            .collect { poke ->
                Log.i("DetailsViewModel", poke.toString())
                pokeState.emit(UiState.Success(poke))
            }
    }


    data class ViewState(
        val imageBitmap: ImageBitmap? = null,
        val colorPalette: Palette? = null
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun setImageUri(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.use { inputStream: InputStream ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                _viewState.emit(
                    ViewState(
                        imageBitmap = bitmap.asImageBitmap(),
                        colorPalette = Palette.from(bitmap).generate()
                    )
                )
                }
            }
        }
    }
