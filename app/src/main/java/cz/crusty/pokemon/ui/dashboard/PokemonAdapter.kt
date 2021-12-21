package cz.crusty.pokemon.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.crusty.pokemon.R
import cz.crusty.pokemon.repository.remote.model.PokemonListResponse
import timber.log.Timber

class PokemonAdapter(val onClick: (PokemonListResponse.Item, position: Int) -> Unit) : RecyclerView.Adapter<PokemonViewHolder>() {

    private val items: ArrayList<PokemonListResponse.Item> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return layout(parent, R.layout.item_pokemon) {
            PokemonViewHolder(it, onClick)
        }
    }

    private inline fun layout(parent: ViewGroup, layoutRes: Int, function: (view: View) -> PokemonViewHolder): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            layoutRes, parent, false)
        return function.invoke(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int  = items.size

    fun add(newItems: List<PokemonListResponse.Item>) {
        Timber.d("adding new %d, items %d", newItems.size, items.size)
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}