package id.madhanra.submission.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.core.R
import id.madhanra.submission.core.databinding.ItemForRvBinding
import id.madhanra.submission.core.domain.model.Movies

class MovieAdapter(private val listener: OnItemClickListener) : PagedListAdapter<Movies, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK){

    val onItemClick: ((Movies) -> Unit)? = null

    companion object {
        const val IF_MOVIE = 100
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemForRvBinding = ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemForRvBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(private val binding: ItemForRvBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item : Movies? = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(movie: Movies) {
            with(binding) {
                val releaseYear = movie.releaseDate.split("-").toTypedArray()
                tvItemYear.text = releaseYear[0]
                tvItemTitle.text = movie.title
                tvItemOverview.text = movie.overview
                Glide.with(itemView.context)
                    .load("${movie.baseUrlPoster}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(imgPoster)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movies)
    }

}