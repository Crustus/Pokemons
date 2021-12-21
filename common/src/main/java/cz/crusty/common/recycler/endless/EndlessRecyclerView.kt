package cz.crusty.common.recycler.endless

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var loadMoreListener: EndlessRecyclerListener? = null

    init {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = layoutManager as LinearLayoutManager
                val itemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (itemCount <= (lastVisibleItem + 10)) {
                    loadMoreListener?.loadMore()
                }
            }
        })
    }

    public fun setLoadMoreListener(loadMoreListener: EndlessRecyclerListener) {
        this.loadMoreListener = loadMoreListener
    }
}