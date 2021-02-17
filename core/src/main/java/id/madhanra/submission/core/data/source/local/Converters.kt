package id.madhanra.submission.core.data.source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.madhanra.submission.core.data.source.remote.response.GenresItem
import id.madhanra.submission.core.data.source.remote.response.GenresItemTvShow

class GenresItemTvShowConverter{

    private val gson = Gson()

    @TypeConverter
    fun fromGenresItemTvShow(genresItemTvShow: List<GenresItemTvShow>): String{
        return gson.toJson(genresItemTvShow)
    }

    @TypeConverter
    fun toGenresItemTvShow(name: String): List<GenresItemTvShow> {
        val listGenres = object:TypeToken<List<GenresItemTvShow>>(){
        }.type
        return gson.fromJson(name, listGenres)
    }
}

class GenresItemConverter{
    private val gson = Gson()

    @TypeConverter
    fun fromGenresItem(genresItem: List<GenresItem>): String{
        return gson.toJson(genresItem)
    }

    @TypeConverter
    fun toGenresItem(name: String): List<GenresItem> {
        val listGenres = object:TypeToken<List<GenresItem>>(){
        }.type
        return gson.fromJson(name, listGenres)
    }
}

class RunTimeConverter{
    private val gson = Gson()

    @TypeConverter
    fun fromList(time: List<Int>): String{
        return gson.toJson(time)
    }

    @TypeConverter
    fun toList(time: String): List<Int>{
        val listTime = object :TypeToken<List<Int>>(){}.type
        return gson.fromJson(time, listTime)
    }
}