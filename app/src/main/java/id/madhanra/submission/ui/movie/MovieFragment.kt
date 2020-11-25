package id.madhanra.submission.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.R
import kotlinx.android.synthetic.main.fragment_movie.*

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val movieAdapter = MovieAdapter()
            viewModel.getMovies().observe(viewLifecycleOwner, {
                movieAdapter.setMovies(it)
                movieAdapter.notifyDataSetChanged()
            })

            viewModel.getLoading().observe(viewLifecycleOwner, {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            with(rv_movies){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }
}