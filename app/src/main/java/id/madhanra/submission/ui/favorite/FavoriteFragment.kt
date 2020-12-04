package id.madhanra.submission.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import id.madhanra.submission.R
import id.madhanra.submission.databinding.FragmentFavoriteBinding
import id.madhanra.submission.utils.SectionsPagerAdapter

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionsPagerAdapter = activity?.let { SectionsPagerAdapter(it) }
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position->
            when(position) {
                0 -> tab.text = resources.getString(R.string.movie_fav)
                1 -> tab.text = resources.getString(R.string.tv_show_fav)
            }
        }.attach()
    }

}