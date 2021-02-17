package id.madhanra.submission.ui.favorite

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.madhanra.submission.R
import id.madhanra.submission.ui.favorite.movie.FavMovieFragment
import id.madhanra.submission.ui.favorite.tvShow.FavTvShowFragment

class SectionsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movie_fav , R.string.tv_show_fav)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavMovieFragment()
            1 -> FavTvShowFragment()
            else -> Fragment()
        }

}