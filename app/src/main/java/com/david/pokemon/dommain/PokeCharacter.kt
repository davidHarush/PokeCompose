package com.david.pokemon.dommain

data class PokeCoreDataCharacter(
    val id: String,
    val name: String,
)

fun PokeCoreDataCharacter.getImage() =
//    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

data class PokeCharacter(
    val coreData: PokeCoreDataCharacter,
    val stats: List<Stat>,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
) {
    override fun toString(): String {

        return "${coreData.name} {stats:  $stats}"
    }
}

data class Stat(
    val power: Int,
    val name: String,
)