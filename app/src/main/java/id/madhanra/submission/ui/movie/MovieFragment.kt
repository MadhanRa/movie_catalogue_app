package id.madhanra.submission.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.madhanra.submission.R
import id.madhanra.submission.core.ui.MovieAdapter
import id.madhanra.submission.core.utils.SortUtils
import id.madhanra.submission.core.vo.Status
import id.madhanra.submission.databinding.FragmentMovieBinding
import id.madhanra.submission.ui.detail.DetailActivity
import id.madhanra.submission.utils.Utils.showSnackBar
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()

    private val sortAZ = "A-Z"
    private val sortZA = "Z-A"
    private val sortDEFAULT = "Default"

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var bottomNavigationView: BottomNavigationView

    private var currentPage = 1
    private var maxPage = 6
    private var lastBottomLocation = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPage(currentPage, SortUtils.DEFAULT)

        loadUI()
        viewModelObserver()
    }

    private fun loadUI() {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        movieAdapter = MovieAdapter()
        with(binding){
            rvMovies.layoutManager = LinearLayoutManager(context)
            rvMovies.hasFixedSize()
            rvMovies.adapter = movieAdapter
            rvMovies.isNestedScrollingEnabled = false

            movieAdapter.onItemClick = { selectedItem ->
                val intent = Intent(activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                    putExtra(DetailActivity.EXTRA_CODE, MovieAdapter.IF_MOVIE)
                }
                startActivity(intent)
            }

            nestedScrollMovie.setOnScrollChangeListener(
                    NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                        val height = (v?.getChildAt(0)?.measuredHeight ?: 0) - (v?.measuredHeight ?: 0)
                        if (scrollY == height && scrollY > lastBottomLocation) {
                            if (currentPage < maxPage) {
                                viewModel.setPage(++currentPage, SortUtils.DEFAULT)
                                lastBottomLocation = scrollY
                                showToast(requireContext(), "Load more.")
                            } else {
                                showToast(requireContext(), "Reach Max")
                            }
                        }
                    }
            )
        }

    }

    private fun viewModelObserver() {
        viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
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
                        retrySnackBar(movies.message)
                        showToast(requireContext(), "Terjadi Kesalahan")
                    }
                }
            }
        })
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
        viewModel.setPage(currentPage, sort)
        viewModel.getMovies().observe(this, { movies ->
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
                        showToast(requireContext(), "Terjadi Kesalahan")                    }
                }
            }
        })
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun retrySnackBar(message: String?) {
        showSnackBar(bottomNavigationView, message) {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }
}