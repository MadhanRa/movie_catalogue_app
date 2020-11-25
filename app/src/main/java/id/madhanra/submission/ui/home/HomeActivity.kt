package id.madhanra.submission.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

import id.madhanra.submission.R
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.elevation = 0f


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        view_pager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, view_pager) {tab, position->
            when(position) {
                0 -> tab.text = resources.getString(R.string.movie)
                1 -> tab.text = resources.getString(R.string.tv_show)
            }
        }.attach()
    }
}