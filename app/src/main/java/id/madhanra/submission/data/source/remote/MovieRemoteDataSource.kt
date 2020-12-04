package id.madhanra.submission.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.madhanra.submission.data.source.remote.response.DetailMovieResponse
import id.madhanra.submission.data.source.remote.response.MovieResponse
import id.madhanra.submission.data.source.remote.response.MoviesItem
import id.madhanra.submission.utils.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
        private val apiService: MovieApi,
        private val compositeDisposable: CompositeDisposable
){
    fun getMovies(): LiveData<ApiResponse<List<MoviesItem>>>{
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MoviesItem>>>()
        compositeDisposable.add(
                apiService.getPopularMovies(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ moviesResponse ->
                            resultMovies.value = ApiResponse.success(moviesResponse.results)
                            if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        }, {
                            Log.e("GetMovies ", "onFailure: ${it.message.toString()}")
                        })
        )
        return resultMovies
    }

    fun getDetailMovie(id: Int): LiveData<ApiResponse<DetailMovieResponse>>{
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<DetailMovieResponse>>()
        compositeDisposable.add(
                apiService.getMovieDetail(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            resultMovie.value = ApiResponse.success(response)
                            if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        },{
                            Log.e("GetDetailMovie ", "onFailure: ${it.message.toString()}")
                        })
        )
        return resultMovie
    }
}