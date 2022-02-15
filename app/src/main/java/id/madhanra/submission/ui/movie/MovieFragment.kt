package id.madhanra.submission.ui.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.madhanra.submission.R
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.ui.GridViewAdapter
import id.madhanra.submission.databinding.FragmentMovieBinding
import id.madhanra.submission.ui.BannerAdapter
import id.madhanra.submission.ui.detail.DetailActivity
import id.madhanra.submission.utils.OffsetPageTransformer
import id.madhanra.submission.utils.Utils.showSnackBar
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    // Adapter
    private lateinit var gridListAdapter: GridViewAdapter
    private lateinit var bannerAdapter: BannerAdapter

    private lateinit var bottomNavigationView: BottomNavigationView

    private var currentPage = 1
    private var maxPage = 6
    private var lastBottomLocation = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPage(currentPage)

        loadUI()
        viewModelObserver()
    }

    private fun loadUI() {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)

        // Prevent re-shimmer
        if (viewModel.getIsAlreadyShimmering())
            stopShimmering()
        else
            startShimmering()

        bannerAdapter = BannerAdapter(requireContext())
        gridListAdapter = GridViewAdapter()

        gridListAdapter.onItemClicked = { selected ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, selected.id)
            intent.putExtra(DetailActivity.EXTRA_CODE, selected.showType)
            startActivity(intent)
        }

        with(binding) {
            val phoneOrientation = requireActivity().resources.configuration.orientation
            val spanCount = if (phoneOrientation == Configuration.ORIENTATION_PORTRAIT) 3 else 7

            rvPopularMovies.layoutManager = GridLayoutManager(context, spanCount)
            rvPopularMovies.setHasFixedSize(false)
            rvPopularMovies.adapter = gridListAdapter
            rvPopularMovies.isNestedScrollingEnabled = false

            vpMovieBanner.adapter = bannerAdapter
            vpMovieBanner.clipToPadding = false
            vpMovieBanner.clipChildren = false
            vpMovieBanner.offscreenPageLimit = 3
            vpMovieBanner.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

            vpMovieBanner.setPageTransformer(OffsetPageTransformer(offsetPx, pageMarginPx))
            dotsIndicatorBannerMovie.setViewPager2(vpMovieBanner)

            nestedScrollMovie.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                    val height = (v?.getChildAt(0)?.measuredHeight ?: 0) - (v?.measuredHeight ?: 0)
                    if (scrollY == height && scrollY > lastBottomLocation) {
                        if (currentPage < maxPage) {
                            viewModel.setPage(++currentPage)
                            lastBottomLocation = scrollY
                            showToast(requireContext(), getString(R.string.load_more_message))
                        } else {
                            showToast(requireContext(), getString(R.string.reach_max_page_message))
                        }
                    }
                }
            )
        }
    }

    private fun viewModelObserver() {
        viewModel.getPopularMovies().observe(viewLifecycleOwner) { movies ->
            when (movies) {
                is Resource.Loading -> {
                    startShimmering()
                }

                is Resource.Success -> {
                    val data = movies.data as ArrayList<Show>
                    if (!data.isNullOrEmpty()) {
                        gridListAdapter.setList(data)
                        bannerAdapter.setBanner(data)
                        stopShimmering()
                    } else {
                        showToast(requireContext(), getString(R.string.data_not_found))
                        // Show SnackBar for retry load data
                        retrySnackBar(movies.message)
                    }
                    // Prevent re-shimmering
                    viewModel.setAlreadyShimmer()
                }
                else -> {
                    showToast(requireContext(), getString(R.string.data_not_found))
                    // Show SnackBar for retry load data
                    retrySnackBar(movies.message)
                }
            }
        }
    }

    private fun startShimmering() {
        if (lastBottomLocation == 0) {
            with(binding) {
                shimmerLayoutMovies.startShimmer()
                shimmerLayoutMovies.visibility = View.VISIBLE
            }
        }
    }

    private fun stopShimmering() {
        with(binding) {
            shimmerLayoutMovies.stopShimmer()
            shimmerLayoutMovies.visibility = View.GONE
        }
    }

    private fun retrySnackBar(message: String?) {
        showSnackBar(requireContext(), bottomNavigationView, message) {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }
}
