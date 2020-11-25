package id.madhanra.submission.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.R
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.ui.detail.DetailActivity

import kotlinx.android.synthetic.main.item_for_rv.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private var listMovies = ArrayList<MoviesEntity>()

    companion object {
        const val IF_MOVIE = 100
    }

    fun setMovies(movies: List<MoviesEntity>?){
        if(movies.isNullOrEmpty()) return
        listMovies.clear()
        listMovies.addAll(movies)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_rv, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: MoviesEntity) {
            with(itemView){
                tv_item_title.text = movie.title
                tv_item_year.text = movie.releaseDate
                tv_item_overview.text = movie.overview
                setOnClickListener{
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, movie.id)
                        putExtra(DetailActivity.EXTRA_CODE, IF_MOVIE)
                    }
                    context.startActivity(intent)
                }
                Glide.with(context)
                    .load("${movie.baseUrlPoster}${movie.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(img_poster)
            }
        }
    }

}