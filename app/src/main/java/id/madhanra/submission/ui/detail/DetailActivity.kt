package id.madhanra.submission.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import id.madhanra.submission.R
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel : DetailViewModel by viewModels()

    companion object{
        const val EXTRA_ID = "extra_ID"
        const val EXTRA_CODE = "extra_code"
        const val IF_MOVIE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val extras = intent.extras
        if (extras != null) {
            val id = extras.getInt(EXTRA_ID)
            val code = extras.getInt(EXTRA_CODE)
            if (code == IF_MOVIE) {
                supportActionBar?.title = resources.getString(R.string.movie)
                viewModel.setSelectedItem(id.toString())
                viewModel.getDetailMovie().observe(this, {
                    populateContentMovie(it)
                })
                viewModel.getLoading().observe(this, {
                    progress_bar.visibility = if (it) View.VISIBLE else View.GONE
                })
            } else {
                supportActionBar?.title = resources.getString(R.string.tv_show)
                viewModel.setSelectedItem(id.toString())
                viewModel.getDetailTvShow().observe(this, {
                    populateContentTvShow(it)
                })
                viewModel.getLoadingTvShow().observe(this, {
                    progress_bar.visibility = if (it) View.VISIBLE else View.GONE
                })
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
            .into(image_poster)
        text_title.text = content.title
        text_year.text = content.releaseDate
        text_genre.text = genre
        text_length.text = length
        text_user_score.text = score
        text_overview.text = content.overview
        text_tagline.text = content.tagLine
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
                .into(image_poster)
        text_title.text = content.name
        text_year.text = content.firstAirDate
        text_genre.text = genre
        text_length.text = length
        text_user_score.text = score
        text_overview.text = content.overview
        text_tagline.text = content.tagLine
    }
}