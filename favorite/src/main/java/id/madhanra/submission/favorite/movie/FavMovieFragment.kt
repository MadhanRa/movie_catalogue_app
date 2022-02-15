package id.madhanra.submission.favorite.movie

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
import id.madhanra.submission.favorite.databinding.FragmentFavMovieBinding
import id.madhanra.submission.favorite.di.favoriteModule
import id.madhanra.submission.ui.detail.DetailActivity
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.scope.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named

class FavMovieFragment : Fragment() {

    private val scopeId = "MovieFavoriteScope"
    private val moduleMovieFavorite = getKoin().getOrCreateScope(scopeId, named(Const.VIEW_MODEL))
    private val viewModel: FavMovieViewModel by moduleMovieFavorite.viewModel(this)

    private var _binding: FragmentFavMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: GridViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        loadUI()
        viewModelObserve()
    }

    private fun loadUI() {
        movieAdapter = GridViewAdapter()
        movieAdapter.onItemClicked = { selectedItem ->
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                putExtra(DetailActivity.EXTRA_CODE, selectedItem.showType)
            }
            startActivity(intent)
        }
        with(binding){
            rvFavMovies.layoutManager = GridLayoutManager(context, 3)
            rvFavMovies.setHasFixedSize(false)
            rvFavMovies.adapter = movieAdapter
        }
    }

    private fun viewModelObserve() {
        viewModel.getFavMovies().observe(viewLifecycleOwner) { movies ->
            when (movies) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    val data = movies.data
                    binding.progressBar.visibility = View.GONE

                    if (!data.isNullOrEmpty()) {
                        movieAdapter.setList(data as ArrayList<Show>)
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
        moduleMovieFavorite.close()
    }

}