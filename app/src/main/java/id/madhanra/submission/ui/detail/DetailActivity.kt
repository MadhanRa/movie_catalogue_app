package id.madhanra.submission.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import id.madhanra.submission.R
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.ui.HorizontalViewAdapter
import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.core.utils.Utils.dateParseToYear
import id.madhanra.submission.databinding.ActivityDetailBinding
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()

    private lateinit var binding: ActivityDetailBinding

    private lateinit var similarShowAdapter: HorizontalViewAdapter

    private lateinit var id: String
    private var code: Int = 0

    companion object {
        const val EXTRA_ID = "extra_ID"
        const val EXTRA_CODE = "extra_code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get Intent id and showtype
        id = intent.getStringExtra(EXTRA_ID).toString()
        code = intent.getIntExtra(EXTRA_CODE, 0)

        // set showid and showtype to triggering load data
        viewModel.setShowIdAndType(id, code)

        loadUI()

        populateContentDetail()
        viewModelObserveSimilarList()
        viewModelObservePopularList()
    }

    private fun loadUI() {
        startShimmering() // Shimmer for detail show
        startShimmeringList() // Shimmer for similar

        // Initialize adapter for similar
        similarShowAdapter = HorizontalViewAdapter()
        similarShowAdapter.onItemClicked = { selected ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, selected.id)
            intent.putExtra(EXTRA_CODE, selected.showType)
            startActivity(intent)
            finish()
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        with(binding) {
            rvSimilarMovies.layoutManager = layoutManager
            rvSimilarMovies.hasFixedSize()
            rvSimilarMovies.adapter = similarShowAdapter
        }

    }

    private fun populateContentDetail() {
        viewModel.getShow().observe(this) { detailShow ->
            when (detailShow) {
                is Resource.Loading -> {
                    startShimmering()
                }

                is Resource.Success -> {
                    val show = detailShow.data
                    if (show != null) {
                        // Favorite button text value
                        val btFavoriteText =
                            if (show.isFavorite == 0) {
                                "Add to Favorite"
                            } else {
                                "Remove from Favorite"
                            }

                        with(binding) {
                            textTitle.text = show.title
                            textYear.text = dateParseToYear(show.releaseDate)
                            textOverview.text = show.overview
                            btFavorite.text = btFavoriteText

                            //Backdrop
                            Picasso.get()
                                .load(Const.URL_BASE_IMAGE + show.backdropPath)
                                .resize(Const.BACKDROP_TARGET_WIDTH, Const.BACKDROP_TARGET_HEIGHT)
                                .error(R.drawable.ic_error)
                                .placeholder(R.drawable.backdrop_placeholder)
                                .into(imageBackdrop)

                            //Poster
                            Picasso.get()
                                .load(Const.URL_BASE_IMAGE + show.posterPath)
                                .resize(Const.POSTER_TARGET_WIDTH, Const.POSTER_TARGET_HEIGHT)
                                .error(R.drawable.ic_error)
                                .placeholder(R.drawable.poster_placeholder)
                                .into(imagePoster)

                            btFavorite.setOnClickListener {
                                viewModel.setFavorite(show)
                            }
                        }
                        stopShimmering()
                    }
                }

                is Resource.Error -> {
                    showSnackBar(detailShow.message)
                    stopShimmering()
                }
            }
        }

        viewModel.setAlreadyShimmer()
    }

    private fun viewModelObserveSimilarList() {
        viewModel.getSimilarList().observe(this) { similarList ->
            when (similarList) {
                is Resource.Loading -> {
                    startShimmeringList()
                }

                is Resource.Success -> {
                    val data = similarList.data
                    if (data.isNullOrEmpty()) {
                        showToast(this, "No Similar list found. Default list will be shown.")
                        viewModel.setListEmptyTrigger()
                    } else {
                        similarShowAdapter.setShimmer(viewModel.getIsAlreadyShimmer())
                        similarShowAdapter.setList(data as ArrayList<Show>)
                        stopShimmeringList()
                    }
                }

                is Resource.Error -> {
                    showToast(this, "List failed to load")
                    showSnackBar(similarList.message)
                    stopShimmeringList()
                }
            }
        }
    }

    private fun viewModelObservePopularList() {
        viewModel.getPopularList().observe(this) { popularList ->
            when (popularList) {
                is Resource.Loading -> {
                    startShimmeringList()
                }

                is Resource.Success -> {
                    val data = popularList.data
                    if (data.isNullOrEmpty()) {
                        binding.similarMoviesTitle.visibility = View.GONE
                        showToast(this, "No Popular list found ")
                    } else {
                        similarShowAdapter.setShimmer(viewModel.getIsAlreadyShimmer())
                        similarShowAdapter.setList(data as ArrayList<Show>)
                        stopShimmeringList()
                    }
                }

                is Resource.Error -> {
                    showToast(this, "List failed to load")
                    showSnackBar(popularList.message)
                    stopShimmeringList()
                }
            }

        }
    }

    private fun showSnackBar(message: String?) {
        val showMessage = message ?: "Unknown Error"
        Snackbar.make(binding.root, showMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction("RETRY") {
                viewModel.setShowIdAndType(id, code)
            }.show()
    }

    private fun stopShimmering() {
        with(binding) {
            imagePoster.stopLoading()
            textTitle.stopLoading()
            textYear.stopLoading()
            textOverview.stopLoading()
            btFavorite.stopLoading()
        }
    }

    private fun startShimmering() {
        with(binding) {
            imagePoster.startLoading()
            textTitle.startLoading()
            textYear.startLoading()
            textOverview.startLoading()
            btFavorite.startLoading()
        }
    }

    private fun startShimmeringList() {
        with(binding) {
            rvSimilarMovies.visibility = View.GONE
            shimmerLayoutSimilarMovies.visibility = View.VISIBLE
            shimmerLayoutSimilarMovies.startShimmer()
        }
    }

    private fun stopShimmeringList() {
        with(binding) {
            rvSimilarMovies.visibility = View.VISIBLE
            shimmerLayoutSimilarMovies.visibility = View.GONE
            shimmerLayoutSimilarMovies.stopShimmer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.removeAllViewsInLayout()
    }
}