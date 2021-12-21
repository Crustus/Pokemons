package cz.crusty.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.crusty.common.R

open class ValueTitleAdapter() : RecyclerView.Adapter<ValueTitleViewHolder>() {

    protected var items: List<ValueTitle>? = null

    open val layoutToInflate = R.layout.item_value_title

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueTitleViewHolder {
        return layout(parent, layoutToInflate) {
            ValueTitleViewHolder(it)
        }
    }

    private inline fun layout(parent: ViewGroup, layoutRes: Int, function: (view: View) -> ValueTitleViewHolder): ValueTitleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            layoutRes, parent, false)
        return function.invoke(view)
    }

    override fun onBindViewHolder(holder: ValueTitleViewHolder, position: Int) {
        items?.run {
            holder.bind(this[position])
        }
    }

    override fun getItemCount(): Int  = items?.size ?: 0

    fun set(items: List<ValueTitle>) {
        this.items = items
        notifyDataSetChanged()
    }
}