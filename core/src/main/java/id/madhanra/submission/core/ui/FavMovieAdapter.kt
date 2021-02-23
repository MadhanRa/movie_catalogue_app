package id.madhanra.submission.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.core.R
import id.madhanra.submission.core.databinding.ItemForRvBinding
import id.madhanra.submission.core.domain.model.Movies

class FavMovieAdapter : RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>(){

    var onItemClick: ((Movies) -> Unit)? = null
    private var movieList = ArrayList<Movies>()

    fun setList(movie: ArrayList<Movies>){
        movieList.clear()
        movieList.addAll(movie)
        notifyDataSetChanged()
    }

    companion object {
        const val IF_MOVIE = 100
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavMovieViewHolder {
        val itemForRvBinding = ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavMovieViewHolder(itemForRvBinding)
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class FavMovieViewHolder(private val binding: ItemForRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movies) {
            with(binding) {
                val releaseYear = movie.releaseDate.split("-").toTypedArray()
                tvItemYear.text = releaseYear[0]
                tvItemTitle.text = movie.title
                tvItemOverview.text = movie.overview
                itemView.setOnClickListener{
                    onItemClick?.invoke(movie)
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