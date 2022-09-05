package ir.nilva.abotorab.helper

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

fun Context.toastError(text:String) = Toasty.error(this, text, Toast.LENGTH_LONG, true).show()

fun Context.toastWarning(text:String) = Toasty.warning(this, text, Toast.LENGTH_LONG, true).show()

fun Context.toastSuccess(text:String) = Toasty.success(this, text, Toast.LENGTH_LONG, true).show()
