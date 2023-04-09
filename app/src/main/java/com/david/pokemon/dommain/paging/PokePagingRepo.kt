package com.david.pokemon.dommain.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.david.pokemon.dommain.IPokeRepo
import javax.inject.Inject

class PokePagingRepo @Inject constructor(
) {

    fun getPokeList(pokeRepo: IPokeRepo) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            PokePagingSource(pagingRepo = pokeRepo)
        }
    ).flow
}

