package id.madhanra.submission.data.source.local.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.madhanra.submission.data.source.local.GenresItemConverter
import id.madhanra.submission.data.source.local.GenresItemTvShowConverter
import id.madhanra.submission.data.source.local.RunTimeConverter
import id.madhanra.submission.data.source.local.entity.DetailMovieEntity
import id.madhanra.submission.data.source.local.entity.DetailTvShowEntity
import id.madhanra.submission.data.source.local.entity.MoviesEntity
import id.madhanra.submission.data.source.local.entity.TvShowEntity

@Database(entities = [MoviesEntity::class, TvShowEntity::class, DetailMovieEntity::class, DetailTvShowEntity::class],
version = 1,
exportSchema = false)
@TypeConverters(GenresItemTvShowConverter::class, GenresItemConverter::class, RunTimeConverter::class)
abstract class MovieCatalogueDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}