package cz.crusty.common.adapter

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_value_title.view.*
import kotlinx.android.synthetic.main.item_value_title.view.title
import kotlinx.android.synthetic.main.item_value_title.view.value
import kotlinx.android.synthetic.main.item_value_title_circle.view.*

class ValueTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: ValueTitle) {
        if (TextUtils.isEmpty(item.value)) {
            itemView.value.visibility = View.GONE
        }
        else {
            itemView.value.text = item.value
            itemView.value.visibility = View.VISIBLE

            // TODO ya not good practice :)
            if (itemView.circle != null) {
                item.value?.toFloat()?.let {
                    itemView.circle.setPercentage(it)
                }
                item.colorPair?.let {
                    itemView.circle.setColors(it)
                }
            }
        }

        itemView.title.text = item.title
    }

}
