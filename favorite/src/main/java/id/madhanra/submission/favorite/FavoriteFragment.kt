package id.madhanra.submission.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.madhanra.submission.R
import id.madhanra.submission.favorite.databinding.FragmentFavoriteBinding
import id.madhanra.submission.favorite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private var mediator : TabLayoutMediator? = null
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
        mediator = TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position->
            when(position) {
                0 -> tab.text = resources.getString(R.string.movie_fav)
                1 -> tab.text = resources.getString(R.string.tv_show_fav)
            }
        }
        mediator?.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        binding.root.removeAllViewsInLayout()
        _binding = null
    }

}