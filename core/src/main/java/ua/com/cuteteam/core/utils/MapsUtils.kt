package ua.com.cuteteam.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import javax.inject.Inject

class MapsUtils @Inject constructor(val context: Context) {

    fun resizeMapIcons(iconId: Int, width: Int, height: Int): Bitmap? {
        val b = BitmapFactory.decodeResource(context.resources, iconId)
        return Bitmap.createScaledBitmap(b, width, height, false)
    }
}
