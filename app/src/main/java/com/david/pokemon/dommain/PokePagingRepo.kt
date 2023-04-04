package com.david.pokemon.dommain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PokePagingRepo @Inject constructor(
) {

    fun getPokeList(viewModelScope: CoroutineScope, pokemonRepo: PokemonRepo) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            PokePagingSource(viewModelScope = viewModelScope, pagingRepo = pokemonRepo)
        }
    ).flow
}

