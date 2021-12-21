package cz.crusty.pokemon.ui.dashboard

import android.net.Uri
import cz.crusty.common.util.logThread
import cz.crusty.pokemon.base.BaseViewModel
import cz.crusty.pokemon.repository.remote.API
import cz.crusty.pokemon.repository.remote.model.PokemonListResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent

class DashboardViewModel : BaseViewModel() {

    private val api: API by KoinJavaComponent.inject(API::class.java)

    private val _pokemon: MutableStateFlow<PokemonListResponse?> = MutableStateFlow(null)
    val pokemon: StateFlow<PokemonListResponse?> = _pokemon

    val pageSize = 30
    var page = 0

    init {
        loadPokemons()
    }

    fun loadPokemons() {
        call(
            onRequest = {
                api.getPokemons(pageSize, page++ * pageSize)
            },
            onSuccess = {
                logThread("onSuccess $it")
                // calculate fake ID, just for demo purpose
                it?.results?.forEach {
                    it.id = Uri.parse(it.url).lastPathSegment?.toInt()
                }
                _pokemon.value = it
            },
            onError = {
                logThread("onError $it")
            }
        )
    }

}