package id.madhanra.submission.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import id.madhanra.submission.R
import id.madhanra.submission.data.DataEntity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_ID = "extra_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]


        val extras = intent.extras
        if (extras != null) {
            val id = extras.getString(EXTRA_ID)
            if (id != null) {
                if (id.contains("m")) {
                    supportActionBar?.title = resources.getString(R.string.movie)
                    viewModel.setSelectedItem(id)
                    populateContent(viewModel.getMovie())
                } else {
                    supportActionBar?.title = resources.getString(R.string.tv_show)
                    viewModel.setSelectedItem(id)
                    populateContent(viewModel.getTvShow())
                }
            }
        }
    }

    private fun populateContent(content: DataEntity) {

        Glide.with(this)
            .load(content.imagePath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
            .into(image_poster)
        text_title.text = content.title
        text_year.text = content.year
        text_certification.text = content.certification
        text_genre.text = content.genre
        text_length.text = content.length
        text_user_score.text = content.userScore
        text_overview.text = content.overview
        text_tagline.text = content.tagline
    }
}