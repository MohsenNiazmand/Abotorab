package ir.nilva.abotorab.helper

import android.content.Context
import android.net.ConnectivityManager
import ir.nilva.abotorab.ApplicationContext
import org.json.JSONObject


fun dp(dps: Int): Int {
    val scale = ApplicationContext.context.resources.displayMetrics.density
    return (dps * scale + 0.5f).toInt()
}

fun mapCabinetLabel(code: String): String? {
    val mapping = defaultCache()["ROW_MAPPING"] ?: ""
    val rowCode = code.substring(2, 3).toInt()
    val columnCode = code.substring(3).toInt()
    return if(mapping.isNotEmpty()){
        val mappedRow = JSONObject(mapping).get(rowCode.toString())
        FormatHelper.toPersianNumber("$columnCode$mappedRow")
    } else {
        FormatHelper.toPersianNumber("$rowCode$columnCode")
    }
}


fun mapCabinetLabelWithCab(code: String): String? {
    if(code.isEmpty()) return ""
    val mapping = defaultCache()["ROW_MAPPING"] ?: ""
    val cabCode = code.substring(0, 2).toInt()
    val rowCode = code.substring(2, 3).toInt()
    val columnCode = code.substring(3).toInt()
    return if(mapping.isNotEmpty()){
        val mappedRow = JSONObject(mapping).get(rowCode.toString())
        FormatHelper.toPersianNumber("$columnCode$mappedRow$cabCode")
    } else {
        FormatHelper.toPersianNumber("$cabCode$rowCode$columnCode")
    }
}

fun Context.isWifiConnected(): Boolean {
    val cm =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return if (netInfo != null && netInfo.isConnectedOrConnecting) {
        val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        mobile != null && mobile.isConnectedOrConnecting || wifi != null && wifi.isConnectedOrConnecting
    } else false
}