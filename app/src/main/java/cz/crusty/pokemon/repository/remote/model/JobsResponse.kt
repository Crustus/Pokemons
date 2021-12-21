package cz.crusty.pokemon.repository.remote.model

data class JobsResponse(
    val jobs: List<Job>
) {
    data class Job (
        val name: String
    )
}
