package cz.crusty.pokemon.ui.detail

import cz.crusty.pokemon.base.BaseViewModel
import cz.crusty.common.util.logThread
import cz.crusty.pokemon.repository.remote.API
import cz.crusty.pokemon.repository.remote.model.PokemonResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel(
    private val api: API,
    private val url: String
) : BaseViewModel() {

    private val _pokemon: MutableStateFlow<PokemonResponse?> = MutableStateFlow(null)
    val pokemon: StateFlow<PokemonResponse?> = _pokemon

    init {

        loadData()

    }

    private fun loadData() {
        call(
            onRequest = {
                api.getPokemon(url)
            },
            onSuccess = {
                logThread("onSuccess $it")
                _pokemon.value = it
            },
            onError = {
                logThread("onError $it")
            }
        )
    }
}