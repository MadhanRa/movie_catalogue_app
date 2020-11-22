package id.madhanra.submission.ui.movie

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.R
import id.madhanra.submission.data.DataEntity
import id.madhanra.submission.ui.detail.DetailActivity

import kotlinx.android.synthetic.main.item_for_rv.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    private var listMovies = ArrayList<DataEntity>()

    fun setMovies(movies: List<DataEntity>?){
        if(movies == null) return
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
        fun bind(movie: DataEntity) {
            with(itemView){
                tv_item_title.text = movie.title
                tv_item_year.text = movie.year
                tv_item_overview.text = movie.overview
                setOnClickListener{
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, movie.id)
                    }
                    val options = ViewCompat.getTransitionName(img_poster)?.let { it1 ->
                        ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity,  img_poster,
                            it1
                        )
                    }
                    if (options != null) {
                        context.startActivity(intent, options.toBundle())
                    }
                }
                Glide.with(context)
                    .load(movie.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(img_poster)
            }
        }
    }

}