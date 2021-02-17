package id.madhanra.submission.core.data

import id.madhanra.submission.core.data.source.remote.ApiResponse
import id.madhanra.submission.core.data.source.remote.StatusResponse
import id.madhanra.submission.core.utils.AppExecutors
import id.madhanra.submission.core.vo.Resource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
//        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe { value ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(value)){
                    fetchFromNetwork()
                } else {
                    result.onNext(Resource.success(value))
                }
            }
        mCompositeDisposable.add(db)
    }

    protected fun onFetchFailed(){}

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.onNext(Resource.loading(null))
        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete{
                mCompositeDisposable.dispose()
            }
            .subscribe { response ->
                when (response.status) {
                    StatusResponse.SUCCESS -> {
                        response.body?.let { saveCallResult(it) }
                        val dbSource = loadFromDb()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.success(it))
                            }
                    }
                    StatusResponse.EMPTY -> {
                        val dbSource = loadFromDb()
                        dbSource.subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe {
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.success(it))
                            }
                    }
                    StatusResponse.ERROR -> {
                        onFetchFailed()
                        result.onNext(Resource.error(response.message, null))
                    }
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> =
        result.toFlowable(BackpressureStrategy.BUFFER)
}