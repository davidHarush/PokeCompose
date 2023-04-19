package com.david.pokemon.dommain

data class PokeCoreDataCharacter(
    val id: String,
    val name: String,
)

fun PokeCoreDataCharacter.getImage() =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

data class PokeCharacter(
    val coreData: PokeCoreDataCharacter,
    val stats: List<Stat>,
    val height: Int,
    val weight: Int,
    val experience: Int,
) {
    override fun toString(): String {

        return "${coreData.name} {stats:  $stats}"
    }

    companion object {
        fun getEmpty(): PokeCharacter = PokeCharacter(
            height = 0,
            weight = 0, experience = 0,
            coreData = PokeCoreDataCharacter("-1", "EMPTY"), stats = emptyList()
        )
    }
}

data class Stat(
    val power: Int,
    val name: String,
)