package id.madhanra.submission.ui.favorite.tvShow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.databinding.FragmentFavTvShowBinding


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val tvShowAdapter = FavTvShowAdapter()
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getFavTvShows().observe(viewLifecycleOwner, { movies ->
                binding.progressBar.visibility = View.GONE
                tvShowAdapter.submitList(movies)
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