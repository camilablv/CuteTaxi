package ua.com.cuteteam.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ua.com.cuteteam.core.application.AppClass

class Utils {

    fun resizeMapIcons(iconId: Int, width: Int, height: Int): Bitmap? {
        val b = BitmapFactory.decodeResource(AppClass.appContext().resources, iconId)
        return Bitmap.createScaledBitmap(b, width, height, false)
    }
}
