package id.madhanra.submission.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import id.madhanra.submission.R
import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.favorite.databinding.FragmentFavoriteBinding
import id.madhanra.submission.favorite.di.favoriteModule
import org.koin.android.ext.android.getKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val sectionsPagerAdapter = activity?.let { SectionsPagerAdapter(it) }
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position->
            when(position) {
                0 -> tab.text = resources.getString(R.string.movie_fav)
                1 -> tab.text = resources.getString(R.string.tv_show_fav)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }

}