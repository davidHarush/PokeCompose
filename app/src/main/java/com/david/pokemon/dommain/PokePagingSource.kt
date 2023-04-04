package com.david.pokemon.dommain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CoroutineScope

class PokePagingSource(
    private val viewModelScope: CoroutineScope,
    private val pagingRepo: PokemonRepo
) :
    PagingSource<Int, PokeCoreDataCharacter>() {


    override fun getRefreshKey(state: PagingState<Int, PokeCoreDataCharacter>): Int? {
        Log.i("ddddddPaging", "getRefreshKey ")

        val key =  state.anchorPosition?.let { anchorPosition ->
            Log.i("ddddddPaging", "anchorPosition: "+ state.anchorPosition.toString())
            Log.i("ddddddPaging", "prevKey: "+  state.closestPageToPosition(anchorPosition)?.prevKey.toString())
            Log.i("ddddddPaging", "nextKey: "+ state.closestPageToPosition( anchorPosition)?.nextKey.toString())

            state.closestPageToPosition(anchorPosition = anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = anchorPosition)?.nextKey?.minus(
                    other = 1
                )
        }


        return key
    }

    override val keyReuseSupported: Boolean
        get() = false

    override val jumpingSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeCoreDataCharacter> {

        return try {

            val page = params.key ?: 0

            val offset = page * 20
            val response = pagingRepo.getPokemonList(offset = offset)
//            val response = pagingRepo.getPage(page = page)


            val pokeList = arrayListOf<PokeCoreDataCharacter>()

            response.collect {
                it.forEach { poke ->

                    pokeList.add(
                        element = PokeCoreDataCharacter(
                            id = poke.id,
                            name = poke.name.capitalize()
                        )
                    )
                }
            }

            val nextKey = page + 1
            val prevKey = if (page == 0) null else page.minus(1)



            LoadResult.Page(
                data = pokeList,
                prevKey = prevKey,
                nextKey = nextKey,
            )

        } catch (e: Exception) {
            Log.e("ddddddPaging", "err", e)
            LoadResult.Error(throwable = e)
        }
    }
}