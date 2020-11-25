package id.madhanra.submission.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.remote.ApiService
import id.madhanra.submission.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.data.source.remote.response.MovieResponse
import id.madhanra.submission.data.source.remote.response.MoviesItem
import id.madhanra.submission.utils.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val apiService: ApiService, private val compositeDisposable: CompositeDisposable){

    private val listMovieResults = MutableLiveData<List<MoviesEntity>>()
    private val movieResult = MutableLiveData<DetailMovieEntity>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMovies(): LiveData<List<MoviesEntity>>{
        EspressoIdlingResource.increment()
        _isLoading.value = true
        compositeDisposable.add(
            apiService.getPopularMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ moviesResponse ->
                    _isLoading.value = false
                    val movieList = ArrayList<MoviesEntity>()
                    val movieResultList: List<MoviesItem>? = moviesResponse.results
                    if ((!movieResultList.isNullOrEmpty())) {
                        for (movie in movieResultList) {
                            val movieData = MoviesEntity(
                                movie.id,
                                movie.overview,
                                movie.title,
                                movie.posterPath,
                                movie.releaseDate
                            )
                            movieList.add(movieData)
                        }
                        listMovieResults.postValue(movieList)
                    }
                }, {
                    _isLoading.value = false
                    listMovieResults.postValue(null)
                    Log.e("GetMovies ", "onFailure: ${it.message.toString()}")

                })
        )
        if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        return listMovieResults
    }

    fun getDetailMovie(id: Int): LiveData<DetailMovieEntity>{
        EspressoIdlingResource.increment()
        _isLoading.value = true
        compositeDisposable.add(
            apiService.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _isLoading.value = false
                    val detailMovieData = DetailMovieEntity(
                        response?.title,
                        response?.genres,
                        response?.overview,
                        response?.runtime,
                        response?.posterPath,
                        response?.releaseDate,
                        response?.voteAverage,
                        response?.tagLine,
                        response?.id
                    )
                    movieResult.postValue(detailMovieData)
                }, {
                    _isLoading.value = false
                    movieResult.postValue(null)
                    Log.e("GetDetailMovie ", "onFailure: ${it.message.toString()}")
                })
        )
        if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        return movieResult
    }

    fun clearComposite() {
        compositeDisposable.dispose()
    }

}

