package id.madhanra.submission.ui.favorite.tvShow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.R
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.databinding.ItemForRvBinding
import id.madhanra.submission.ui.detail.DetailActivity

class FavTvShowAdapter : PagedListAdapter<TvShowEntity ,FavTvShowAdapter.FavTvShowViewHolder>(DIFF_CALLBACK) {

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<TvShowEntity>(){
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavTvShowViewHolder {
        val itemForRvBinding = ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavTvShowViewHolder(itemForRvBinding)
    }

    override fun onBindViewHolder(holder: FavTvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null)
        holder.bind(tvShow)
    }

    class FavTvShowViewHolder(private val binding: ItemForRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                tvItemTitle.text = tvShow.name
                tvItemYear.text = tvShow.firstAirDate
                tvItemOverview.text = tvShow.overview
                itemView.setOnClickListener{
                    val intent = Intent (itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ID, tvShow.id)
                        putExtra(DetailActivity.EXTRA_CODE, 0)
                    }
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load("${tvShow.baseUrlPoster}${tvShow.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .centerCrop()
                    .into(imgPoster)

            }
        }
    }

}