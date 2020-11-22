package id.madhanra.submission.ui.tvShow

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

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {
    private var listTvShows = ArrayList<DataEntity>()

    fun setTvShows(tvShows: List<DataEntity>?) {
        if(tvShows == null) return
        listTvShows.clear()
        listTvShows.addAll(tvShows)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_for_rv, parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTvShows[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listTvShows.size

    class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: DataEntity) {
            with(itemView) {
                tv_item_title.text = tvShow.title
                tv_item_year.text = tvShow.year
                tv_item_overview.text = tvShow.overview
                setOnClickListener{
                    val intent = Intent (context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, tvShow.id)
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
                    .load(tvShow.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(img_poster)

            }
        }

    }

}