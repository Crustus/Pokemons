package cz.crusty.pokemon.repository.remote.model

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Item>
) {

    data class Item(
        /** custom added ID since we dont want to flood PokeApi to get images in Pokemons list */
        var id: Int?,
        val name: String,
        val url: String
    )

}
