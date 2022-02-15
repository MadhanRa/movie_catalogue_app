package id.madhanra.submission.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.madhanra.submission.R
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.databinding.ItemBannerBinding
import id.madhanra.submission.ui.detail.DetailActivity

class BannerAdapter(val context: Context) : RecyclerView.Adapter <BannerAdapter.BannerViewHolder>() {

    private var bannerList = ArrayList<Show>()

    fun setBanner(shows: ArrayList<Show>) {
        val bannerCount = 5
        bannerList.clear()
        bannerList.addAll(shows.take(bannerCount))
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    class BannerViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemBannerBinding.bind(item)
        fun bind(show: Show) {
            with(binding) {
                tvBannerTitle.text = show.title

                Picasso.get()
                    .load(Const.URL_BASE_IMAGE + show.backdropPath)
                    .resize(Const.BACKDROP_TARGET_WIDTH, Const.BACKDROP_TARGET_HEIGHT)
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.backdrop_placeholder)
                    .into(ivBanner)

                root.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ID, show.id)
                    intent.putExtra(DetailActivity.EXTRA_CODE, show.showType)

                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}