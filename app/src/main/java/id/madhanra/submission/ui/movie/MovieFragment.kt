package id.madhanra.submission.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.R
import id.madhanra.submission.core.domain.model.Movies
import id.madhanra.submission.core.ui.MovieAdapter
import id.madhanra.submission.databinding.FragmentMovieBinding
import id.madhanra.submission.core.utils.SortUtils
import id.madhanra.submission.core.vo.Status
import id.madhanra.submission.ui.detail.DetailActivity

@AndroidEntryPoint
class MovieFragment : Fragment(), MovieAdapter.OnItemClickListener {

    private val sortAZ = "A-Z"
    private val sortZA = "Z-A"
    private val sortDEFAULT = "Default"

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            movieAdapter = MovieAdapter(this)


            viewModel.getMovies(SortUtils.DEFAULT).observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> {
                            binding.errorNotification.visibility = View.GONE
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorNotification.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                            movieAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorNotification.visibility = View.VISIBLE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(binding.rvMovies){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId){
            R.id.action_az -> sort = sortAZ
            R.id.action_za -> sort = sortZA
            R.id.action_default -> sort = sortDEFAULT
        }
        viewModel.getMovies(sort).observe(this, { movies ->
            if (movies != null) {
                when (movies.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        movieAdapter.submitList(movies.data)
                        movieAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(movie: Movies) {
        val intent = Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, movie.id)
                    putExtra(DetailActivity.EXTRA_CODE, MovieAdapter.IF_MOVIE)
        }
        startActivity(intent)
    }
}