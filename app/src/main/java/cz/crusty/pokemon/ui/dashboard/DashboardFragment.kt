package cz.crusty.pokemon.ui.dashboard

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import cz.crusty.common.recycler.VerticalSpaceItemDivider
import cz.crusty.common.recycler.endless.EndlessRecyclerListener
import cz.crusty.common.util.dpToPx
import cz.crusty.common.util.flowWhenResumed
import cz.crusty.common.util.logThread
import cz.crusty.pokemon.R
import cz.crusty.pokemon.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.coroutines.flow.collect

class DashboardFragment : BaseFragment() {

    private lateinit var homeViewModel: DashboardViewModel

    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        homeViewModel.apply {

            flowWhenResumed {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_camera -> {
                findNavController().navigate(DashboardFragmentDirections.actionNavDashboardToCameraFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        setTitle(getString(R.string.pokemons))
        setSubtitle("")
    }
}