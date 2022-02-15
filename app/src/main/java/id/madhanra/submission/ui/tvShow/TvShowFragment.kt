package id.madhanra.submission.ui.tvShow

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
import id.madhanra.submission.databinding.FragmentTvShowBinding
import id.madhanra.submission.ui.BannerAdapter
import id.madhanra.submission.ui.detail.DetailActivity
import id.madhanra.submission.utils.OffsetPageTransformer
import id.madhanra.submission.utils.Utils
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModel()

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var tvShowAdapter: GridViewAdapter

    private lateinit var bottomNavigationView: BottomNavigationView

    private var currentPage = 1
    private var maxPage = 6
    private var lastBottomLocation = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
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

        tvShowAdapter = GridViewAdapter()
        bannerAdapter = BannerAdapter(requireContext())

        tvShowAdapter.onItemClicked = { selected ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, selected.id)
            intent.putExtra(DetailActivity.EXTRA_CODE, selected.showType)
            startActivity(intent)
        }

        with(binding) {
            val phoneOrientation = requireActivity().resources.configuration.orientation
            val spanCount = if (phoneOrientation == Configuration.ORIENTATION_PORTRAIT) 3 else 7

            rvPopularTvShows.layoutManager = GridLayoutManager(context, spanCount)
            rvPopularTvShows.setHasFixedSize(false)
            rvPopularTvShows.adapter = tvShowAdapter
            rvPopularTvShows.isNestedScrollingEnabled = false

            vpTvShowsBanner.adapter = bannerAdapter
            vpTvShowsBanner.clipToPadding = false
            vpTvShowsBanner.clipChildren = false
            vpTvShowsBanner.offscreenPageLimit = 3
            vpTvShowsBanner.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

            vpTvShowsBanner.setPageTransformer(OffsetPageTransformer(offsetPx, pageMarginPx))
            dotsIndicatorBannerTvShows.setViewPager2(vpTvShowsBanner)

            nestedScrollTvShow.setOnScrollChangeListener(
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
        viewModel.getTvShows().observe(viewLifecycleOwner) { tvShows ->
            when (tvShows) {
                is Resource.Loading -> {
                    startShimmering()
                }

                is Resource.Success -> {
                    val data = tvShows.data as ArrayList<Show>
                    if (!data.isNullOrEmpty()) {
                        tvShowAdapter.setList(data)
                        bannerAdapter.setBanner(data)
                        stopShimmering()
                    } else {
                        showToast(requireContext(), getString(R.string.data_not_found))
                        // Show SnackBar for retry load data
                        retrySnackBar(tvShows.message)
                    }
                    // Prevent re-shimmering
                    viewModel.setAlreadyShimmer()
                }
                else -> {
                    showToast(requireContext(), getString(R.string.data_not_found))
                    // Show SnackBar for retry load data
                    retrySnackBar(tvShows.message)
                }
            }
        }
    }

    private fun retrySnackBar(message: String?) {
        Utils.showSnackBar(requireContext(), bottomNavigationView, message) {
            viewModel.refresh()
        }
    }

    private fun startShimmering() {
        if (lastBottomLocation == 0) {
            with(binding) {
                shimmerLayoutTvShows.startShimmer()
                shimmerLayoutTvShows.visibility = View.VISIBLE
            }
        }
    }

    private fun stopShimmering() {
        with(binding) {
            shimmerLayoutTvShows.stopShimmer()
            shimmerLayoutTvShows.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }
}