package ir.nilva.abotorab.helper

import android.content.Context
import android.net.wifi.SupplicantState
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.util.*


fun Context.connectToNetworkWPA(text: String): Boolean {
    return connectToNetworkWPA("amanatdari$text", "110+salavat")
}

fun Context.connectToNetworkWPA(networkSSID: String, password: String): Boolean {
    return try {
        val conf = WifiConfiguration()
        conf.SSID =
            "\"" + networkSSID + "\"" // Please note the quotes. String should contain SSID in quotes
        conf.preSharedKey = "\"" + password + "\""
        conf.status = WifiConfiguration.Status.ENABLED
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
        conf.hiddenSSID = true; // Put this line to hidden SSID
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
        conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
        Log.d("connecting", conf.SSID + " " + conf.preSharedKey)
        val wifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        var newId = wifiManager.addNetwork(conf)
        if (newId == -1) {
            newId = wifiManager.getExistingNetworkId(conf.SSID)
        }

        Log.d("after connecting", conf.SSID + " " + conf.preSharedKey)
        wifiManager.disconnect()
        wifiManager.enableNetwork(newId, true)
        wifiManager.reconnect()
        true
    } catch (ex: Exception) {
        println(Arrays.toString(ex.stackTrace))
        false
    }

}

private fun WifiManager.getExistingNetworkId(SSID: String): Int {
    val configuredNetworks: List<WifiConfiguration> = configuredNetworks
    if (configuredNetworks != null) {
        for (existingConfig in configuredNetworks) {
            if (SSID.equals(existingConfig.SSID, ignoreCase = true)) {
                return existingConfig.networkId
            }
        }
    }
    return -1
}

fun Context.getConnectedSSID(): String? {
    val wifiManager =
        applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    val wifiInfo: WifiInfo = wifiManager.connectionInfo
    if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
        return wifiInfo.ssid
    }
    return null
}

fun Context.isConnectedWifiValid(): Boolean {
    val ssid = getConnectedSSID() ?: return true
    val server : String = defaultCache()["depository_code"] ?: return false
    return ssid == server
}