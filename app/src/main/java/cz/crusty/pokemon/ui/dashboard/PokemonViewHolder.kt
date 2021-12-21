package cz.crusty.pokemon.ui.dashboard

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cz.crusty.pokemon.repository.remote.model.PokemonListResponse
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonViewHolder(itemView: View, val onClick: (PokemonListResponse.Item, position: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PokemonListResponse.Item) {
        itemView.name.text = item.name
        // fake ID used, we relay on repeated image scheme
        itemView.image.setImageURI(Uri.parse("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.id}.png"))
        itemView.setOnClickListener {
            onClick.invoke(item, adapterPosition)
        }
    }

}
