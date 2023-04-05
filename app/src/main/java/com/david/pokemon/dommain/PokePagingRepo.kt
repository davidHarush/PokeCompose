package com.david.pokemon.dommain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class PokePagingRepo @Inject constructor(
) {

    fun getPokeList(pokemonRepo: PokemonRepo) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            PokePagingSource(pagingRepo = pokemonRepo)
        }
    ).flow
}

