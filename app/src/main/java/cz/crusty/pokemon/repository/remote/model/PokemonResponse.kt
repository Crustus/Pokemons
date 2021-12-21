package cz.crusty.pokemon.repository.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse (
    val id: Long,
    val name: String,

    @SerializedName("base_experience")
    val baseExperience: Long,

    val height: Long,

    @SerializedName("is_default")
    val isDefault: Boolean,

    val order: Long,
    val weight: Long,
    val abilities: List<Ability>,
    val forms: List<Species>,

    @SerializedName("game_indices")
    val gameIndices: List<GameIndex>,

    @SerializedName("held_items")
    val heldItems: List<HeldItem>,

    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String,

    val moves: List<Move>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,

    @SerializedName("past_types")
    val pastTypes: List<PastType>
)

data class Ability (
    @SerializedName("is_hidden")
    val isHidden: Boolean,

    val slot: Long,
    val ability: Species
)

data class Species (
    val name: String,
    val url: String
)

data class GameIndex (
    @SerializedName("game_index")
    val gameIndex: Long,

    val version: Species
)

data class HeldItem (
    val item: Species,

    @SerializedName("version_details")
    val versionDetails: List<VersionDetail>
)

data class VersionDetail (
    val rarity: Long,
    val version: Species
)

data class Move (
    val move: Species,

    @SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetail>
)

data class VersionGroupDetail (
    @SerializedName("level_learned_at")
    val levelLearnedAt: Long,

    @SerializedName("version_group")
    val versionGroup: Species,

    @SerializedName("move_learn_method")
    val moveLearnMethod: Species
)

data class PastType (
    val generation: Species,
    val types: List<Type>
)

data class Type (
    val slot: Long,
    val type: Species
)

data class Sprites (
    @SerializedName("back_default")
    val backDefault: String?,

    @SerializedName("back_female")
    val backFemale: String?,

    @SerializedName("back_shiny")
    val backShiny: String?,

    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,

    @SerializedName("front_default")
    val frontDefault: String?,

    @SerializedName("front_female")
    val frontFemale: String?,

    @SerializedName("front_shiny")
    val frontShiny: String?,

    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?,
)

data class Stat (
    @SerializedName("base_stat")
    val baseStat: Long,

    val effort: Long,
    val stat: Species
)
