package id.madhanra.submission.core.data.source.remote

import android.util.Log
import id.madhanra.submission.core.data.source.remote.response.DetailTvShowResponse
import id.madhanra.submission.core.data.source.remote.response.TvShowsItem
import id.madhanra.submission.core.utils.EspressoIdlingResource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class TvShowRemoteDataSource(
    private val apiService: TvShowApi,
    private val compositeDisposable: CompositeDisposable
) {
    fun getTvShows(page: Int): Flowable<ApiResponse<List<TvShowsItem>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = PublishSubject.create<ApiResponse<List<TvShowsItem>>>()
        compositeDisposable.add(
            apiService.getPopularTvShow(page)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ tvShowsResponse ->
                    resultTvShows.onNext(if (tvShowsResponse.results.isNotEmpty()) ApiResponse.success(tvShowsResponse.results) else ApiResponse.empty("Nothing"))
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }, {
                    resultTvShows.onNext(ApiResponse.error(it.message.toString()))
                    Log.e("GetTvShows ", "onFailure: $it")
                })
        )
        return resultTvShows.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailTvShow(id: Int): Flowable<ApiResponse<DetailTvShowResponse>>{
        EspressoIdlingResource.increment()
        val resultTvShow = PublishSubject.create<ApiResponse<DetailTvShowResponse>>()
        compositeDisposable.add(
            apiService.getTvShowDetail(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe({ response ->
                    resultTvShow.onNext(if (response.name.isNotEmpty()) ApiResponse.success(response) else ApiResponse.empty("Nothing"))
                    if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }, {
                    resultTvShow.onNext(ApiResponse.error(it.message.toString()))
                    Log.e("getDetailTvShow ", "onFailure: $it")
                })
        )
        return resultTvShow.toFlowable(BackpressureStrategy.BUFFER)
    }
}