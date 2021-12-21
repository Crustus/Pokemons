package cz.crusty.pokemon.repository.remote

import cz.crusty.pokemon.repository.remote.model.JobsResponse
import cz.crusty.pokemon.repository.remote.model.NewJobResponse
import cz.crusty.pokemon.repository.remote.model.PokemonListResponse
import cz.crusty.pokemon.repository.remote.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {

    @POST("?")
    suspend fun postNewJob(): Response<NewJobResponse>

    @GET("?")
    suspend fun getJobs(): Response<JobsResponse>

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int, @Query("offset")  offset: Int): Response<PokemonListResponse>

    @GET
    suspend fun getPokemon(@Url url: String): Response<PokemonResponse>

}