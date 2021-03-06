package id.madhanra.submission.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.ui.FavMovieAdapter
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

    private lateinit var movieAdapter: FavMovieAdapter


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
        movieAdapter = FavMovieAdapter()
        movieAdapter.onItemClick = { selectedItem ->
            val intent = Intent(activity, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                putExtra(DetailActivity.EXTRA_CODE, FavMovieAdapter.IF_MOVIE)
            }
            startActivity(intent)
        }
        with(binding){
            progressBar.visibility = View.VISIBLE
            rvFavMovies.layoutManager = LinearLayoutManager(context)
            rvFavMovies.setHasFixedSize(true)
            rvFavMovies.adapter = movieAdapter
        }
    }

    private fun viewModelObserve() {
        viewModel.getFavMovies().observe(viewLifecycleOwner, { movies ->
            binding.progressBar.visibility = View.GONE
            if (movies.isEmpty()) {
                binding.viewEmpty.root.visibility = View.VISIBLE
            } else {
                binding.viewEmpty.root.visibility = View.GONE
            }
            movieAdapter.setList(movies as ArrayList<Movies>)
        })
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