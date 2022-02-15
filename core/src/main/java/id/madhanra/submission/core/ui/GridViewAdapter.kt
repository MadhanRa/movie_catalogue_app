package id.madhanra.submission.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.madhanra.submission.core.R
import id.madhanra.submission.core.databinding.ItemForRvGridBinding
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const

class GridViewAdapter : RecyclerView.Adapter<GridViewAdapter.PosterViewHolder>() {
    private var showList = ArrayList<Show>()
    var onItemClicked: ((Show) -> Unit)? = null

    fun setList(shows: ArrayList<Show>) {
        showList.clear()
        showList.addAll(shows)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridViewAdapter.PosterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_rv_grid, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewAdapter.PosterViewHolder, position: Int) {
        holder.bind(showList[position])
    }

    override fun getItemCount(): Int = showList.size

    inner class PosterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemForRvGridBinding.bind(item)
        fun bind(show: Show) {
            with(binding) {

                Picasso.get()
                    .load(Const.URL_BASE_IMAGE + show.posterPath)
                    .resize(Const.POSTER_TARGET_WIDTH, Const.POSTER_TARGET_HEIGHT)
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.poster_placeholder)
                    .into(ivPoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(showList[adapterPosition])
            }
        }

    }
}