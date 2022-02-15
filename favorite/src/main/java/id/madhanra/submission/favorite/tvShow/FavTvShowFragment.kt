package id.madhanra.submission.favorite.tvShow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.ui.GridViewAdapter
import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.favorite.databinding.FragmentFavTvShowBinding
import id.madhanra.submission.favorite.di.favoriteModule
import id.madhanra.submission.ui.detail.DetailActivity
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.scope.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named


class FavTvShowFragment : Fragment() {

    private val scopeId = "TvShowFavoriteScope"
    private val moduleTvShowFavorite = getKoin().getOrCreateScope(scopeId, named(Const.VIEW_MODEL))
    private val viewModel: FavTvShowViewModel by moduleTvShowFavorite.viewModel(this)


    private var _binding: FragmentFavTvShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvShowAdapter: GridViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavTvShowBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        loadUI()
        viewModelObserver()
    }

    private fun loadUI() {
        tvShowAdapter = GridViewAdapter()
        tvShowAdapter.onItemClicked = { selectedItem ->
            val intent = Intent (activity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                putExtra(DetailActivity.EXTRA_CODE, selectedItem.showType)
            }
            startActivity(intent)
        }
        with(binding){
            progressBar.visibility = View.VISIBLE
            rvFavTvShow.layoutManager = GridLayoutManager(context, 3)
            rvFavTvShow.setHasFixedSize(false)
            rvFavTvShow.adapter = tvShowAdapter
        }
    }

    private fun viewModelObserver() {
        viewModel.getFavTvShows().observe(viewLifecycleOwner) { tvShow ->
            when (tvShow) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    val data = tvShow.data
                    binding.progressBar.visibility = View.GONE

                    if (!data.isNullOrEmpty()) {
                        tvShowAdapter.setList(data as ArrayList<Show>)
                        binding.nothingNotification.visibility = View.GONE
                    } else {
                        binding.nothingNotification.visibility = View.VISIBLE
                    }
                }

                is Resource.Error -> {
                    binding.nothingNotification.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        moduleTvShowFavorite.close()
    }
}