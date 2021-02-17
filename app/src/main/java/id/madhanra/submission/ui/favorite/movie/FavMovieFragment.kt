package id.madhanra.submission.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.core.ui.FavMovieAdapter
import id.madhanra.submission.databinding.FragmentFavMovieBinding
import id.madhanra.submission.ui.detail.DetailActivity

@AndroidEntryPoint
class FavMovieFragment : Fragment() {

    private var _binding: FragmentFavMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val movieAdapter = FavMovieAdapter()
            movieAdapter.onItemClick = { selectedItem ->
                val intent = Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                    putExtra(DetailActivity.EXTRA_CODE, FavMovieAdapter.IF_MOVIE)
                }
                startActivity(intent)
            }
            binding.progressBar.visibility = View.VISIBLE

            viewModel.getFavMovies().observe(viewLifecycleOwner, { movies ->
                binding.progressBar.visibility = View.GONE
                movieAdapter.submitList(movies)
                movieAdapter.notifyDataSetChanged()
            })

            with(binding.rvFavMovies){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}