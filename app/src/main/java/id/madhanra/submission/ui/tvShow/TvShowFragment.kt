package id.madhanra.submission.ui.tvShow

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.madhanra.submission.R
import id.madhanra.submission.core.ui.TvShowAdapter
import id.madhanra.submission.core.utils.SortUtils
import id.madhanra.submission.core.vo.Status
import id.madhanra.submission.databinding.FragmentTvShowBinding
import id.madhanra.submission.ui.detail.DetailActivity
import id.madhanra.submission.utils.Utils
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by viewModel()

    private val sortAZ = "A-Z"
    private val sortZA = "Z-A"
    private val sortDEFAULT = "Default"

    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvShowAdapter: TvShowAdapter

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

        viewModel.setPage(currentPage, SortUtils.DEFAULT)

        loadUI()
        viewModelObserver()
    }

    private fun loadUI() {
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)

        tvShowAdapter = TvShowAdapter()

        with(binding){
            rvTvShow.layoutManager = LinearLayoutManager(context)
            rvTvShow.hasFixedSize()
            rvTvShow.adapter = tvShowAdapter
            rvTvShow.isNestedScrollingEnabled = false

            tvShowAdapter.onItemClick = { selectedItem ->
                val intent = Intent (activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                    putExtra(DetailActivity.EXTRA_CODE, 0)
                }
                startActivity(intent)
            }

            nestedScrollTvShow.setOnScrollChangeListener(
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
        viewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
            if (tvShows != null) {
                when (tvShows.status) {
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.viewError.root.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.GONE
                        tvShowAdapter.submitList(tvShows.data)
                        tvShowAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        retrySnackBar(tvShows.message)
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
        viewModel.getTvShows().observe(this, { tvShow ->
            if (tvShow != null) {
                when (tvShow.status) {
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        tvShowAdapter.submitList(tvShow.data)
                        tvShowAdapter.notifyDataSetChanged()
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

    private fun retrySnackBar(message: String?) {
        Utils.showSnackBar(bottomNavigationView, message) {
            viewModel.refresh()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _binding = null
    }
}