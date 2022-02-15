package id.madhanra.submission.ui.detail

import androidx.lifecycle.*
import id.madhanra.submission.core.domain.usecase.MoviesUseCase
import id.madhanra.submission.core.domain.usecase.TvShowsUseCase
import id.madhanra.submission.core.data.Resource
import id.madhanra.submission.core.data.source.local.entity.DoubleTrigger
import id.madhanra.submission.core.domain.model.Show
import id.madhanra.submission.core.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieUseCase: MoviesUseCase,
    private val tvShowUseCase: TvShowsUseCase,
    ): ViewModel() {

    // If already Shimmering
    private var isAlreadyShimmer: Boolean = false

    //Trigger by showId and showType to load detail and similar list
    private var doubleTrigger = MutableLiveData<DoubleTrigger>()

    //Trigger when similar list is empty
    private var listEmptyTrigger = MutableLiveData<Unit>()

    //Get detail
    private var show = doubleTrigger.switchMap {
        when (it.showType) {
            Const.MOVIE_TYPE ->
                movieUseCase.getDetailMovie(it.showId).asLiveData()
            else ->
                tvShowUseCase.getDetailTvShow(it.showId).asLiveData()
        }
    }

    // Get Similar List
    private var similarList = doubleTrigger.switchMap {
        when (it.showType) {
            Const.MOVIE_TYPE ->
                movieUseCase.getSimilarMovies(it.showId).asLiveData()
            else ->
                tvShowUseCase.getSimilarTvShows(it.showId).asLiveData()
        }
    }

    // Get popular list
    // Triggered when similar list is empty
    private var popularList = listEmptyTrigger.switchMap {
        val showType = doubleTrigger.value?.showType
        val page = 1

        when (showType) {
            Const.MOVIE_TYPE ->
                movieUseCase.getPopularMovies(page).asLiveData()
            else ->
                tvShowUseCase.getAllTvShows(page).asLiveData()
        }
    }

    fun setAlreadyShimmer() {
        isAlreadyShimmer = true
    }

    fun setShowIdAndType(show_id: String, show_type: Int) {
        doubleTrigger.postValue(DoubleTrigger(show_id, show_type))
    }

    fun setListEmptyTrigger() {
        listEmptyTrigger.postValue(Unit)
    }

    fun setFavorite(show: Show) {
        viewModelScope.launch(Dispatchers.IO) {
            show.isFavorite = if (show.isFavorite == 0) 1 else 0
            when (show.showType) {
                Const.MOVIE_TYPE ->
                    movieUseCase.setFavorite(show)
                else ->
                    tvShowUseCase.setFavorite(show)
            }
        }
    }

    fun getShow(): LiveData<Resource<Show>> = show

    fun getIsAlreadyShimmer() = isAlreadyShimmer

    fun getSimilarList(): LiveData<Resource<List<Show>>> = similarList

    fun getPopularList(): LiveData<Resource<List<Show>>> = popularList

}