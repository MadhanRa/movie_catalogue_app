package id.madhanra.submission.core.utils


import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object SortUtils {
    const val AZ = "A-Z"
    const val ZA = "Z-A"
    const val DEFAULT = "Default"

    fun getSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM moviesentities")
        when (filter){
            AZ -> simpleQuery.append(" ORDER BY movieTitle ASC")
            ZA -> simpleQuery.append(" ORDER BY movieTitle DESC")
            DEFAULT -> simpleQuery.append("")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}

object TvShowSortUtils {
    const val AZ = "A-Z"
    const val ZA = "Z-A"
    const val DEFAULT = "Default"

    fun getSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM tvshowentities")
        when (filter){
            AZ -> simpleQuery.append(" ORDER BY tvShowName ASC")
            ZA -> simpleQuery.append(" ORDER BY tvShowName DESC")
            DEFAULT -> simpleQuery.append("")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}