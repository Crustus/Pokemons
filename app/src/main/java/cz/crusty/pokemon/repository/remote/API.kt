package cz.crusty.pokemon.repository.remote

import cz.crusty.pokemon.repository.remote.model.JobsResponse
import cz.crusty.pokemon.repository.remote.model.PokemonListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class API {

    val api by lazy {
        Timber.d("lazy init API")
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client)
            .build()

        retrofit.create(APIService::class.java)
    }

    // Different API request approach, just for Demo
    fun getJobs(result: (Result<JobsResponse?>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = doRequest {
                api.getJobs()
            }
            result.invoke(response)
        }
    }

    private suspend fun <T> doRequest(request: suspend () -> Response<T>): Result<T?> {
        return try {
            val response = request.invoke()
            if (response.isSuccessful) {
                //logThread("doReq")
                Result.Success(response.body())
            } else {
                Result.Error(response.code(), response.message())
            }
        } catch (throwable: Throwable) {
            Result.Error(throwable)
        }
    }

    // TODO this two methods should be called directly

    suspend fun getPokemons(limit: Int = 20, offset: Int = 0): Response<PokemonListResponse> = api.getPokemonList(limit, offset)

    suspend fun getPokemon(url: String) = api.getPokemon(url)

}