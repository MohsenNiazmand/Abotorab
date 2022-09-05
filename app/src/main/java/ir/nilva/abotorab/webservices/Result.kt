package ir.nilva.abotorab.webservices

import ir.nilva.abotorab.ApplicationContext
import org.jetbrains.anko.toast
import java.net.UnknownHostException

interface Result<T> {
    fun onSuccess(response: T?)
    fun onFailed(throwable: Throwable, errorBody: String) {
        val context = ApplicationContext.context
        when (throwable) {
            is UnknownHostException -> context.toast(errorBody)
            is WebServiceError -> {
                context.toast("لطفا اتصال خود را بررسی کنید")
            }
        }
    }
}
