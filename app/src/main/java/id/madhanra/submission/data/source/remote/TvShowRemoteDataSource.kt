package id.madhanra.submission.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.madhanra.submission.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.data.source.remote.response.TvShowsItem
import id.madhanra.submission.utils.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvShowRemoteDataSource @Inject constructor(
    private val apiService: TvShowApi,
    private val compositeDisposable: CompositeDisposable
) {
    fun getTvShows(): LiveData<ApiResponse<List<TvShowsItem>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowsItem>>>()
        compositeDisposable.add(
            apiService.getPopularTvShow(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tvShowsResponse ->
                    resultTvShows.value = ApiResponse.success(tvShowsResponse.results)
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }, {
                    Log.e("GetTvShows ", "onFailure: ${it.message.toString()}")
                })
        )
        return resultTvShows
    }

    fun getDetailTvShow(id: Int): LiveData<ApiResponse<DetailTvShowResponse>>{
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<DetailTvShowResponse>>()
        compositeDisposable.add(
            apiService.getTvShowDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    resultTvShow.value = ApiResponse.success(response)
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }, {
                    Log.e("getDetailTvShow ", "onFailure: ${it.message.toString()}")
                })
        )
        return resultTvShow
    }
}