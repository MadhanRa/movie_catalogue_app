package id.madhanra.submission.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.madhanra.submission.core.R
import id.madhanra.submission.core.databinding.ItemForRvBinding
import id.madhanra.submission.core.domain.model.TvShows

class FavTvShowAdapter : RecyclerView.Adapter<FavTvShowAdapter.FavTvShowViewHolder>() {

    private var tvShowList = ArrayList<TvShows>()
    var onItemClick: ((TvShows) -> Unit)? = null

    fun setList(tvShow: ArrayList<TvShows>) {
        tvShowList.clear()
        tvShowList.addAll(tvShow)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavTvShowViewHolder {
        val itemForRvBinding = ItemForRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavTvShowViewHolder(itemForRvBinding)
    }

    override fun onBindViewHolder(holder: FavTvShowViewHolder, position: Int) {
        holder.bind(tvShowList[position])
    }

    override fun getItemCount(): Int = tvShowList.size

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