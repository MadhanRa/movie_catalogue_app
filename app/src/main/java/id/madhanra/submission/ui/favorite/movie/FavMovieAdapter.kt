package id.madhanra.submission.ui.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.R
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.databinding.ItemForRvBinding
import id.madhanra.submission.ui.detail.DetailActivity

class FavMovieAdapter : PagedListAdapter<MoviesEntity, FavMovieAdapter.FavMovieViewHolder>(DIFF_CALLBACK){

    companion object {
        const val IF_MOVIE = 100
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<MoviesEntity>(){
            override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavMovieViewHolder {
        val itemForRvBinding = ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavMovieViewHolder(itemForRvBinding)
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    class FavMovieViewHolder(private val binding: ItemForRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MoviesEntity) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemYear.text = movie.releaseDate
                tvItemOverview.text = movie.overview
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, movie.id)
                        putExtra(DetailActivity.EXTRA_CODE, IF_MOVIE)
                    }
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load("${movie.baseUrlPoster}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(imgPoster)
            }
        }
    }
}