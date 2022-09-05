package ir.nilva.abotorab.webservices

import android.content.Context
import android.os.Handler
import android.os.Looper
import ir.nilva.abotorab.ApplicationContext
import ir.nilva.abotorab.helper.isConnectedWifiValid
import ir.nilva.abotorab.helper.toastError
import okhttp3.ResponseBody
import org.jetbrains.anko.runOnUiThread
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException

fun getServices(): WebserviceUrls = MyRetrofit.getInstance().webserviceUrls

suspend fun <T> Context.callWebserviceWithFailure(
    caller: suspend () -> Response<T>,
    failure: (response: String, code: Int?) -> Unit
): T? {
    return try {
        val response = caller()
        if (response.isSuccessful) {
            response.body()
        } else {
            val jsonErr = response.errorBody()?.string()
            val errorMessage =
                JSONObject(jsonErr!!).getJSONArray("non_field_errors").get(0)?.toString()
                    ?: "مشکلی پیش آمده است"
            if (response.code() == 400) {
                toastError(errorMessage)
            }
            failure(errorMessage, response.code())
            null
        }
    } catch (e: Exception) {
        if (this.isConnectedWifiValid()) {
            failure("متاسفانه ارتباط برقرار نشد", -1)
        } else {
            failure(
                "شبکه شما متعلق به این امانت‌داری نیست. لطفا از اپلیکیشن خارج شده و مجدد تنظیم دستی انجام دهید.",
                -1
            )
        }
        null
    }
}

suspend fun <T> Context.callWebservice(
    caller: suspend () -> Response<T>
): T? {
    return try {
        val response = caller()
        if (response.isSuccessful) {
            response.body()
        } else {
            handleError(response.code(), response.errorBody())
            null
        }
    } catch (e: Exception) {
        handleFailure(e)
        null
    }
}

private fun Context.handleError(
    code: Int,
    errorBody: ResponseBody?
) {
    onFailed(WebServiceError(), errorBody?.string() ?: "", code)
}

private fun Context.handleFailure(t: Throwable) {
    if(this.isConnectedWifiValid()){
        if (t is UnknownHostException) {
            onFailed(t, "اتصال خود را بررسی کنید")
        } else {
            onFailed(t, t.message.toString())
        }
    } else {
        onFailed(t, "شبکه شما متعلق به این امانت‌داری نیست. لطفا از اپلیکیشن خارج شده و مجدد تنظیم دستی انجام دهید.")
    }

}

private fun Context.handleException(e: java.lang.Exception) {
    onFailed(e, e.message.toString())
}

fun Context.onFailed(throwable: Throwable, errorBody: String, errorCode: Int = -1) {
    when (throwable) {
        is UnknownHostException -> runOnUiThread {
            toastError("لطفا اتصال خود را بررسی کنید")
        }
        is WebServiceError -> {
            if (errorCode == 403) {
                toastError("شما دسترسی لازم را ندارید")
            } else {
               runOnUiThread {
                   toastError(errorBody)
               }


            }
        }
        else-> toastError(errorBody)
    }
}