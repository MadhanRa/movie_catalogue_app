package id.madhanra.submission.favorite.tvShow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.core.ui.FavTvShowAdapter
import id.madhanra.submission.favorite.databinding.FragmentFavTvShowBinding
import id.madhanra.submission.ui.detail.DetailActivity


@AndroidEntryPoint
class FavTvShowFragment : Fragment() {


    private var _binding: FragmentFavTvShowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavTvShowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavTvShowBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvShowAdapter = FavTvShowAdapter()
            tvShowAdapter.onItemClick = { selectedItem ->
                val intent = Intent (activity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_ID, selectedItem.id)
                    putExtra(DetailActivity.EXTRA_CODE, 0)
                }
                startActivity(intent)
            }
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getFavTvShows().observe(viewLifecycleOwner, { tvShow ->
                binding.progressBar.visibility = View.GONE
                if (tvShow.isEmpty()) {
                    binding.nothingNotification.visibility = View.VISIBLE
                } else {
                    binding.nothingNotification.visibility = View.GONE
                }
                tvShowAdapter.submitList(tvShow)
                tvShowAdapter.notifyDataSetChanged()
            })

            with(binding.rvFavTvShow){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter
            }
        }
    }
}