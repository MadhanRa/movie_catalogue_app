package id.madhanra.submission.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity
import id.madhanra.submission.data.source.remote.ApiService
import id.madhanra.submission.data.source.remote.response.TvShowsItem
import id.madhanra.submission.utils.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowRepository @Inject constructor(private val apiService: ApiService, private val compositeDisposable: CompositeDisposable) {
    private val listTvShowResult = MutableLiveData<List<TvShowEntity>>()
    private val tvShowResult = MutableLiveData<DetailTvShowEntity>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getTvShow(): LiveData<List<TvShowEntity>> {
        EspressoIdlingResource.increment()

        _isLoading.value = true
        compositeDisposable.add(
            apiService.getPopularTvShow(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tvShowsResponse ->
                    _isLoading.value = false
                    val tvShowList = ArrayList<TvShowEntity>()
                    val tvShowResultList: List<TvShowsItem>? = tvShowsResponse?.results
                    if (!tvShowResultList.isNullOrEmpty()) {
                        for (tvShow in tvShowResultList) {
                            val tvShowData = TvShowEntity(
                                tvShow.firstAirDate,
                                tvShow.overview,
                                tvShow.posterPath,
                                tvShow.name,
                                tvShow.id
                            )
                            tvShowList.add(tvShowData)
                        }
                        listTvShowResult.postValue(tvShowList)
                    }
                }, {
                    _isLoading.value = false
                    listTvShowResult.postValue(null)
                    Log.e("GetTvShows ", "onFailure: ${it.message.toString()}")
                })
        )
        if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        return listTvShowResult
    }

    fun getDetailTvShow(id: Int): LiveData<DetailTvShowEntity>{
        EspressoIdlingResource.increment()
        _isLoading.value = true
        compositeDisposable.add(
            apiService.getTvShowDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _isLoading.value = false
                    val detailTvShowData = DetailTvShowEntity(
                        response.genres,
                        response?.firstAirDate,
                        response?.overview,
                        response?.posterPath,
                        response?.voteAverage,
                        response?.name,
                        response?.tagLine,
                        response?.episodeRunTime,
                        response?.id
                    )
                    tvShowResult.postValue(detailTvShowData)
                }, {
                    _isLoading.value = false
                    tvShowResult.postValue(null)
                    Log.e("getDetailTvShow ", "onFailure: ${it.message.toString()}")
                })
        )
        if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        return tvShowResult
    }
}