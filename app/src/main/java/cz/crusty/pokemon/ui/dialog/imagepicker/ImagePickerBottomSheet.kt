package cz.crusty.pokemon.ui.dialog.imagepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import cz.crusty.pokemon.R
import kotlinx.android.synthetic.main.image_picker_bottom_sheet.view.*

class ImagePickerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var clickListener: (Item) -> Unit
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.image_picker_bottom_sheet, container, false)

        view.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        val data = ArrayList<Item>().apply {
            add(Item.Camera(android.R.drawable.ic_menu_camera, "Camera"))
            add(Item.Gallery(android.R.drawable.ic_menu_gallery, "Gallery"))
        }

        adapter = CustomAdapter(data)
        adapter.setOnClickListener {
            dismiss()
            clickListener.invoke(it)
        }
        view.recyclerview.adapter = adapter

        return view
    }

    fun setOnClickListener(clickListener: (Item) -> Unit) {
        this.clickListener = clickListener
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    class CustomAdapter(private val mList: List<Item>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        private lateinit var clickListener: (Item) -> Unit

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.image_picker_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mList[position]
            holder.bind(item, clickListener)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        fun setOnClickListener(clickListener: (Item) -> Unit) {
            this.clickListener = clickListener
        }

        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            private val imageView: ImageView = itemView.findViewById(R.id.imageview)
            private val textView: TextView = itemView.findViewById(R.id.textView)

            fun bind(item: Item, clickListener: (Item) -> Unit) {
                imageView.setImageResource(item.image)
                textView.text = item.text
                itemView.setOnClickListener {
                    clickListener.invoke(item)
                }
            }
        }
    }
}