package com.david.pokemon.dommain

import com.david.pokemon.data.module.Result
import com.david.pokemon.data.module.getId

object Mapper {

    suspend fun convertPokemonList(results: List<Result>): ArrayList<PokeCoreDataCharacter> {
        val list = arrayListOf<PokeCoreDataCharacter>()
        results.forEachIndexed { _, character ->
            list.add(
                PokeCoreDataCharacter(
                    id = character.getId(),
                    name = character.name.capitalize()
                )
            )
        }
        return list
    }

}