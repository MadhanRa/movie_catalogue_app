package id.madhanra.submission.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

import id.madhanra.submission.R
import id.madhanra.submission.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.elevation = 0f


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position->
            when(position) {
                0 -> tab.text = resources.getString(R.string.movie)
                1 -> tab.text = resources.getString(R.string.tv_show)
                2 -> tab.text = resources.getString(R.string.movie_fav)
                3 -> tab.text = resources.getString(R.string.tv_show_fav)
            }
        }.attach()
    }
}