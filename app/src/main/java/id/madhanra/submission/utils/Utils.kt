package id.madhanra.submission.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import id.madhanra.submission.R

object Utils {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(context: Context, view: View, message: String?, runnable: Runnable) {
        val showMessage = message ?: context.getString(R.string.unknown_error)
        Snackbar.make(view, showMessage, Snackbar.LENGTH_INDEFINITE)
            .setAction(context.getString(R.string.retry)) {
                runnable.run()
            }
            .apply {
                anchorView = view
            }
            .show()
    }
}