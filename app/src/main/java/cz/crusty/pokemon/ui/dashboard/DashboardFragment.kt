package cz.crusty.pokemon.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.crusty.common.recycler.VerticalSpaceItemDivider
import cz.crusty.common.recycler.endless.EndlessRecyclerListener
import cz.crusty.common.util.dpToPx
import cz.crusty.pokemon.R
import cz.crusty.pokemon.base.BaseFragment
import cz.crusty.common.util.logThread
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.coroutines.flow.collect

class DashboardFragment : BaseFragment() {

    private lateinit var homeViewModel: DashboardViewModel

    private lateinit var pokemonAdapter: PokemonAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        homeViewModel.apply {

            lifecycleScope.launchWhenResumed {
                pokemon.collect { value ->
                    //logThread("Pokemon ${value?.count}")
                    value?.results?.let { pokemonAdapter.add(it) }
                    setSubtitle("${pokemonAdapter.itemCount} / ${value?.count ?: "0"}")
                }
            }
        }

        root.apply {

            recycler.apply {
                pokemonAdapter = PokemonAdapter { item, position ->
                    logThread("item: $item, pos: $position")
                    findNavController().navigate(DashboardFragmentDirections.actionNavDashboardToDetailFragment(item.url))
                }
                adapter = pokemonAdapter

                addItemDecoration(VerticalSpaceItemDivider(8.dpToPx))

                setLoadMoreListener(object: EndlessRecyclerListener {
                    override fun loadMore() {
                        if (!homeViewModel.loading.value) {
                            homeViewModel.loadPokemons()
                        }
                    }
                })
            }
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        setTitle(getString(R.string.pokemons))
        setSubtitle("")
    }
}