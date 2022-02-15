package id.madhanra.submission.core.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun dateParseToYear(stringDate: String?): String {
        return if (!stringDate.isNullOrEmpty()) {
            try {
                val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val formatter = SimpleDateFormat("yyyy", Locale.ENGLISH)
                val newDate = parser.parse(stringDate)

                if (newDate != null) {
                    formatter.format(newDate)
                } else {
                    stringDate
                }
            } catch (e: Exception) {
                "No Date"
            }
        } else {
            "No Date"
        }
    }
}