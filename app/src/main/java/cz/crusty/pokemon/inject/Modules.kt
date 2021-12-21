package cz.crusty.pokemon.inject

import cz.crusty.pokemon.repository.remote.API
import cz.crusty.pokemon.ui.detail.DetailViewModel
import cz.crusty.pokemon.ui.job.NewJobViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single { API() }

    viewModel { NewJobViewModel(get()) }
    viewModel { params -> DetailViewModel(get(), params.get()) }

}