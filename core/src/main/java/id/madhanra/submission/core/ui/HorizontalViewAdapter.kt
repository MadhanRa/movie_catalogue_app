package id.madhanra.submission.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.core.R
import id.madhanra.submission.core.databinding.ItemForRvBinding
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const

class HorizontalViewAdapter : RecyclerView.Adapter<HorizontalViewAdapter.PosterViewHolder>() {
    private var isAlreadyShimmer: Boolean = false
    private var showList = ArrayList<Show>()
    var onItemClicked: ((Show) -> Unit)? = null

    fun setList(shows: ArrayList<Show>) {
        showList.clear()
        showList.addAll(shows)
        notifyDataSetChanged()
    }

    fun setShimmer(isAlreadyShimmer: Boolean) {
        this.isAlreadyShimmer = isAlreadyShimmer
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalViewAdapter.PosterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_for_rv, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewAdapter.PosterViewHolder, position: Int) {
        val showItem = showList[position]
        holder.bind(showItem, isAlreadyShimmer)
    }

    override fun getItemCount(): Int = showList.size

    inner class PosterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemForRvBinding.bind(item)
        fun bind(show: Show, isAlreadyShimmer: Boolean) {
            with(binding) {

                // Start shimmering
                if (isAlreadyShimmer) ivPosterHorizontal.startLoading()

                // Horizontal poster
                Glide.with(itemView.context)
                    .load(Const.URL_BASE_IMAGE + show.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(ivPosterHorizontal)

                // Stop shimmering
                ivPosterHorizontal.stopLoading()
            }
        }
        init {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(showList[adapterPosition])
            }
        }
    }
}