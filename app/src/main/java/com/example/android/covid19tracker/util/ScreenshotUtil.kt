package com.example.android.covid19tracker.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.android.covid19tracker.R
import java.io.FileOutputStream

// Pick a name for the screenshot file. It doesn't have to be unique because
// we don't need to save it for later. We'll just write over it for future screenshots.
val SCREENSHOT_FILENAME = "screenshot.jpg"

class ScreenshotUtil {
    companion object {

        fun takeScreenshot(view: View): Bitmap {
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            val bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            return bitmap
        }

        fun saveScreenshot(bitmap: Bitmap, activity: Activity): Uri {
            val path = activity.getFileStreamPath(SCREENSHOT_FILENAME)
            var out: FileOutputStream? = null

            try {
                out = activity.openFileOutput(SCREENSHOT_FILENAME, Context.MODE_PRIVATE)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
            } catch (e: Exception) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.error, e.message),
                    Toast.LENGTH_LONG
                ).show()
                Log.e("saveScreenshot", e.message)
            } finally {
                try {
                    out?.close()
                } catch (e: Exception) {
                }
            }
            return FileProvider.getUriForFile(
                activity,
                "com.example.android.covid19tracker.provider", path
            )
        }
    }
}