package ir.nilva.abotorab.helper

import android.content.Context
import android.graphics.Typeface

fun Context.getAppTypeface() = Typeface.createFromAsset(this.assets, "fonts/yekan.ttf")