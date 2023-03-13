package ir.nilva.abotorab.view.page.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.helper.defaultCache
import ir.nilva.abotorab.helper.set
import ir.nilva.abotorab.helper.toastWarning
import ir.nilva.abotorab.view.page.main.LoginActivity
import kotlinx.android.synthetic.main.item_country.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.view


/**
 * Created by mahdihs76 on 9/10/18.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(broadcastReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
        initCalligraphy()
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            checkWifi()
        }
    }

    open fun checkWifi() {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        try {
            val ip: Int = wifiManager.connectionInfo.ipAddress
            if (ip == 0)
                toastWarning(resources.getString(R.string.text_label))



        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showWifiDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_wifi)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        (dialog.findViewById(R.id.yesBtn) as Button).setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                AppDatabase.getInstance().deliveryDao().clear()
                AppDatabase.getInstance().cabinetDao().clear()
                AppDatabase.getInstance().offlineDeliveryDao().clear()

            }
            defaultCache()["token"] = null

            dialog.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        dialog.show()

    }

    private fun initCalligraphy() =
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/yekan.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

}