package ir.nilva.abotorab.helper

import android.app.Activity
import android.util.DisplayMetrics

fun Activity.getScreenWidth() : Int{
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}