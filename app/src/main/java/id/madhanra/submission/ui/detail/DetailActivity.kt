package id.madhanra.submission.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import id.madhanra.submission.R
import id.madhanra.submission.core.domain.model.DetailMovies
import id.madhanra.submission.core.domain.model.DetailTvShows
import id.madhanra.submission.core.utils.Const
import id.madhanra.submission.core.vo.Status
import id.madhanra.submission.databinding.ActivityDetailBinding
import id.madhanra.submission.utils.Utils.showToast
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel : DetailViewModel by viewModel()

    private lateinit var binding: ActivityDetailBinding

    private lateinit var id: String
    private var code: Int = 0

    companion object{
        const val EXTRA_ID = "extra_ID"
        const val EXTRA_CODE = "extra_code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.contentDetail.favoriteButton.apply {
            setMinAndMaxFrame(40, 100 )
        }

        val extras = intent.extras
        if (extras != null) {
            id = extras.getInt(EXTRA_ID).toString()
            code = extras.getInt(EXTRA_CODE)
            viewModel.setSelectedItem(id)
            if (code == Const.IF_MOVIE) {
                supportActionBar?.title = resources.getString(R.string.movie)
                viewModelMovieObserve()
            } else {
                supportActionBar?.title = resources.getString(R.string.tv_show)
                viewModelTvShowObserve()
            }
        }
    }
    private fun viewModelMovieObserve(){
        viewModel.getDetailMovie().observe(this, { dataResponse ->
            if (dataResponse != null)
                when (dataResponse.status) {
                    Status.LOADING -> binding.contentDetail.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.contentDetail.progressBar.visibility = View.GONE
                        if (dataResponse.data != null) {
                            populateContentMovie(dataResponse.data!!)
                            var isFavorite = dataResponse.data!!.favorite
                            setFavoriteState(isFavorite)
                            viewModel.getAMovie().observe(this, {
                                binding.contentDetail.favoriteButton.setOnClickListener { _ ->
                                    viewModel.setFavoriteMovie(dataResponse.data!!, it)
                                    isFavorite = !isFavorite
                                    setFavoriteState(isFavorite)
                                    if (isFavorite) {
                                        showToast(this, "Telah menambah ke favorit")
                                    } else {
                                        showToast(this, "Telah menghapus dari favorit")
                                    }
                                }
                            })
                        }
                    }
                    Status.ERROR -> {
                        binding.contentDetail.progressBar.visibility = View.GONE
                        showSnackBar(dataResponse.message)
                        showToast(this, "Terjadi kesalahan,\n cek koneksi internet anda")
                    }
                }
        })
    }

    private fun viewModelTvShowObserve(){
        viewModel.getDetailTvShow().observe(this, {
            if (it != null)
                when (it.status) {
                    Status.LOADING -> binding.contentDetail.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding.contentDetail.progressBar.visibility = View.GONE
                        if (it.data != null){
                            populateContentTvShow(it.data!!)
                            var isFavorite = it.data!!.favorite
                            setFavoriteState(isFavorite)
                            viewModel.getATvShow().observe(this, { aTvShow ->
                                binding.contentDetail.favoriteButton.setOnClickListener { _ ->
                                    viewModel.setFavoriteTvShow(it.data!!, aTvShow)
                                    isFavorite = !isFavorite
                                    setFavoriteState(isFavorite)
                                    if (isFavorite) {
                                        showToast(this, "Telah menambah ke favorit")
                                    } else {
                                        showToast(this, "Telah menghapus dari favorit")
                                    }
                                }
                            })
                        }
                    }
                    Status.ERROR -> {
                        binding.contentDetail.progressBar.visibility = View.GONE
                        showSnackBar(it.message)
                        showToast(this, "Terjadi kesalahan,\n cek koneksi internet anda")
                    }
                }
        })
    }

    private fun setFavoriteState(favorite: Boolean){
        if (!favorite) {
            binding.contentDetail.favoriteButton.apply{
                speed = -1f
                playAnimation()
            }
        } else {
            binding.contentDetail.favoriteButton.apply {
                speed = 1f
                playAnimation()
            }
        }
    }

    private fun populateContentMovie(content: DetailMovies) {
        val releaseYear = content.releaseDate?.split("-")?.toTypedArray()
        var genre = ""
        if (!content.genres.isNullOrEmpty()) {
            for (x in content.genres!!.indices) {
                if (x == content.genres!!.size - 1) {
                    genre += content.genres!![x].name
                    break
                }
                genre += "${content.genres!![x].name}, "
            }
        }

        var length = ""
        if (content.runtime != null) {
            length = when {
                content.runtime!! >= 60 -> {
                    "1h ${content.runtime!! % 60}m"
                }
                content.runtime!! >= 120 -> {
                    "2h ${content.runtime!! % 120}"
                }
                else -> {
                    "${content.runtime}m"
                }
            }
        }

        var score = ""
        if (content.voteAverage != null) {
            score = "${(content.voteAverage!! * 10).toInt()}%"
        }
        with(binding) {
            Glide.with(this@DetailActivity)
                    .load("${content.baseUrlPoster}${content.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(contentDetail.imagePoster)
            contentDetail.textTitle.text = content.title
            if (releaseYear != null)
                contentDetail.textYear.text = releaseYear[0]
            contentDetail.textGenre.text = genre
            contentDetail.textLength.text = length
            contentDetail.textUserScore.text = score
            contentDetail.textOverview.text = content.overview
            contentDetail.textTagline.text = content.tagLine
        }
    }
    private fun populateContentTvShow(content: DetailTvShows) {
        val firstAirYear = content.firstAirDate?.split("-")?.toTypedArray()
        var genre = ""
        for (x in content.genres.indices) {
            if (x == content.genres.size - 1) {
                genre += content.genres[x].name
                break
            }
            genre += "${content.genres[x].name}, "
        }

        var length = ""
        if (!content.episodeRunTime.isNullOrEmpty()) {
            length = when {
                content.episodeRunTime!![0] >= 60 -> {
                    "1h ${content.episodeRunTime!![0] % 60}m"
                }
                content.episodeRunTime!![0] >= 120 -> {
                    "2h ${content.episodeRunTime!![0] % 120}"
                }
                else -> {
                    "${content.episodeRunTime!![0]}m"
                }
            }
        }

        var score = ""
        if (content.voteAverage != null) {
            score = "${(content.voteAverage!! * 10).toInt()}%"
        }

        with(binding) {
            Glide.with(this@DetailActivity)
                    .load("${content.baseUrlPoster}${content.posterPath}")
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                    .into(contentDetail.imagePoster)
            contentDetail.textTitle.text = content.name
            if (firstAirYear != null)
                contentDetail.textYear.text = firstAirYear[0]
            contentDetail.textGenre.text = genre
            contentDetail.textLength.text = length
            contentDetail.textUserScore.text = score
            contentDetail.textOverview.text = content.overview
            contentDetail.textTagline.text = content.tagLine
        }
    }
    private fun showSnackBar(message: String?) {
        val showMessage = message ?: "Unknown Error"
        Snackbar.make(binding.root, showMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    viewModel.setSelectedItem(id)
                }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.removeAllViewsInLayout()
    }
}