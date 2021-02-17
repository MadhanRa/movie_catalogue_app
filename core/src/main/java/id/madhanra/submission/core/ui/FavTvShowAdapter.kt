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
import id.madhanra.submission.core.domain.model.TvShows

class FavTvShowAdapter : PagedListAdapter<TvShows , FavTvShowAdapter.FavTvShowViewHolder>(
    DIFF_CALLBACK
) {

    var onItemClick: ((TvShows) -> Unit)? = null

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<TvShows>(){
            override fun areItemsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShows, newItem: TvShows): Boolean {
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

    inner class FavTvShowViewHolder(private val binding: ItemForRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShows) {
            with(binding) {
                val firstAirYear = tvShow.firstAirDate.split("-").toTypedArray()
                tvItemYear.text = firstAirYear[0]
                tvItemTitle.text = tvShow.name
                tvItemOverview.text = tvShow.overview
                itemView.setOnClickListener{
                    onItemClick?.invoke(tvShow)
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