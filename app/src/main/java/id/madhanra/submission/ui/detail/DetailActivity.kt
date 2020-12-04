package id.madhanra.submission.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.R
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.databinding.ActivityDetailBinding
import id.madhanra.submission.vo.Status

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val contentBinding = binding.contentDetail

    private val viewModel : DetailViewModel by viewModels()

    companion object{
        const val EXTRA_ID = "extra_ID"
        const val EXTRA_CODE = "extra_code"
        const val IF_MOVIE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contentBinding.favoriteButton.apply {
            setMinAndMaxFrame(40, 100 )
        }

        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_ID)
            val code = extras.getInt(EXTRA_CODE)
            if (code == IF_MOVIE) {
                supportActionBar?.title = resources.getString(R.string.movie)
                viewModel.setSelectedItem(id.toString())
                viewModel.getDetailMovie().observe(this, {
                    if (it != null)
                        when (it.status) {
                            Status.LOADING -> contentBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                contentBinding.progressBar.visibility = View.GONE
                                if (it.data != null){
                                    populateContentMovie(it.data)
                                    val isFavorite = it.data.favorite
                                    setFavoriteState(isFavorite)
                                    viewModel.getAMovie().observe(this, { aMovie ->
                                        contentBinding.favoriteButton.setOnClickListener { _ ->
                                            viewModel.setFavoriteMovie(it.data, aMovie)
                                        }
                                    })
                                }
                            }
                            Status.ERROR -> {
                                contentBinding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                })
            } else {
                supportActionBar?.title = resources.getString(R.string.tv_show)
                viewModel.setSelectedItem(id.toString())
                viewModel.getDetailTvShow().observe(this, {
                    if (it != null)
                        when (it.status) {
                            Status.LOADING -> contentBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                contentBinding.progressBar.visibility = View.GONE
                                if (it.data != null){
                                    populateContentTvShow(it.data)
                                    val isFavorite = it.data.favorite
                                    setFavoriteState(isFavorite)
                                    viewModel.getATvShow().observe(this, { aTvShow ->
                                        contentBinding.favoriteButton.setOnClickListener { _ ->
                                            viewModel.setFavoriteTvShow(it.data, aTvShow)
                                        }
                                    })
                                }
                            }
                            Status.ERROR -> {
                                contentBinding.progressBar.visibility = View.GONE
                                Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                })
            }
        }
    }

    private fun setFavoriteState(favorite: Boolean){
        if (!favorite) {
            contentBinding.favoriteButton.apply{
                speed = -1f
                playAnimation()
            }
        } else {
            contentBinding.favoriteButton.apply {
                speed = 1f
                playAnimation()
            }
        }
    }

    private fun populateContentMovie(content: DetailMovieEntity) {

        var genre = ""
        if (!content.genres.isNullOrEmpty()) {
            for (x in content.genres.indices) {
                if (x == content.genres.size - 1) {
                    genre += content.genres[x].name
                    break
                }
                genre += "${content.genres[x].name}, "
            }
        }

        var length = ""
        if (content.runtime != null) {
            length = when {
                content.runtime >= 60 -> {
                    "1h ${content.runtime % 60}m"
                }
                content.runtime >= 120 -> {
                    "2h ${content.runtime % 120}"
                }
                else -> {
                    "${content.runtime}m"
                }
            }
        }

        var score = ""
        if (content.voteAverage != null) {
            score = "${(content.voteAverage * 10).toInt()}%"
        }
        Glide.with(this)
            .load("${content.baseUrlPoster}${content.posterPath}")
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(contentBinding.imagePoster)
        contentBinding.textTitle.text = content.title
        contentBinding.textYear.text = content.releaseDate
        contentBinding.textGenre.text = genre
        contentBinding.textLength.text = length
        contentBinding.textUserScore.text = score
        contentBinding.textOverview.text = content.overview
        contentBinding.textTagline.text = content.tagLine
    }
    private fun populateContentTvShow(content: DetailTvShowEntity) {

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
                content.episodeRunTime[0] >= 60 -> {
                    "1h ${content.episodeRunTime[0] % 60}m"
                }
                content.episodeRunTime[0] >= 120 -> {
                    "2h ${content.episodeRunTime[0] % 120}"
                }
                else -> {
                    "${content.episodeRunTime[0]}m"
                }
            }
        }

        var score = ""
        if (content.voteAverage != null) {
            score = "${(content.voteAverage * 10).toInt()}%"
        }

        Glide.with(this)
                .load("${content.baseUrlPoster}${content.posterPath}")
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .into(contentBinding.imagePoster)
        contentBinding.textTitle.text = content.name
        contentBinding.textYear.text = content.firstAirDate
        contentBinding.textGenre.text = genre
        contentBinding.textLength.text = length
        contentBinding.textUserScore.text = score
        contentBinding.textOverview.text = content.overview
        contentBinding.textTagline.text = content.tagLine
    }
}