package cz.crusty.pokemon.ui.detail

import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.navigation.fragment.navArgs
import cz.crusty.common.adapter.ValueTitle
import cz.crusty.common.adapter.ValueTitleAdapter
import cz.crusty.common.adapter.ValueTitleCircleAdapter
import cz.crusty.common.util.ColorPalette
import cz.crusty.common.util.dpToPx
import cz.crusty.common.util.flowWhenResumed
import cz.crusty.pokemon.R
import cz.crusty.pokemon.base.BaseFragment
import cz.crusty.pokemon.repository.remote.model.PokemonResponse
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber


class DetailFragment : BaseFragment() {

    private lateinit var statsAdapter: ValueTitleAdapter
    private lateinit var abilitiesAdapter: ValueTitleAdapter
    private lateinit var movesAdapter: ValueTitleAdapter
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModel { parametersOf(args.pokemonUrl) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate() %s", this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_detail, container, false)

        root.apply {

            stats.apply {
                statsAdapter = ValueTitleCircleAdapter()
                adapter = statsAdapter
            }

            abilities.apply {
                abilitiesAdapter = ValueTitleAdapter()
                adapter = abilitiesAdapter
            }

            moves.apply {
                movesAdapter = ValueTitleAdapter()
                adapter = movesAdapter
            }

        }

        viewModel.apply {

            flowWhenResumed {
                pokemon.collect { value ->
                    value?.let { updateUI(it) }
                }
            }

        }

        return root
    }

    private fun updateUI(pokemonResponse: PokemonResponse) {
        pokemonResponse.sprites.frontDefault?.let {
            image.setImageURI(Uri.parse(it))
        }

        name.text = pokemonResponse.name

        params.text = createSpannedParams(pokemonResponse)

        statsAdapter.set(pokemonResponse.stats.map {
            ValueTitle(
                it.baseStat.toString(),
                it.stat.name,
                ColorPalette.getColorsByRange(requireContext(), it.baseStat)
            )
        })

        abilitiesAdapter.set(pokemonResponse.abilities.map {
            ValueTitle(it.slot.toString(), it.ability.name)
        })

        movesAdapter.set(pokemonResponse.moves.sortedBy { it.move.name }.map {
            ValueTitle(null, it.move.name)
        })
    }

    private fun createSpannedParams(pokemonResponse: PokemonResponse): CharSequence {
        return SpannableStringBuilder().apply {
            paramsLine(this, "height\t", pokemonResponse.height.toString())
            paramsLine(this, "weight", pokemonResponse.weight.toString())
            paramsLine(this, "base Exp", pokemonResponse.baseExperience.toString())
        }
    }

    private fun paramsLine(builder: SpannableStringBuilder, title: String, value: String) {
        builder.inSpans(BulletSpan(8.dpToPx, 0xff000000.toInt())) {
            append("$title\t\t")
                .bold {
                    scale(SPAN_PROPORTION) {
                        append(value)
                    }
                }
        }
            .append(NL)
    }

    override fun onStart() {
        super.onStart()
        setTitle(getString(R.string.pokemon))
        setSubtitle("")
    }

    companion object {
        const val NL = '\n';
        const val SPAN_PROPORTION = 1.5f;
    }
}