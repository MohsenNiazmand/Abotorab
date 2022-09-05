package ir.nilva.abotorab.view.page.main

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.work.*
import com.afollestad.materialdialogs.MaterialDialog
//import com.microblink.MicroblinkSDK
import com.ramotion.circlemenu.CircleMenuView
import ir.nilva.abotorab.ApplicationContext
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.helper.*
import ir.nilva.abotorab.view.page.base.BaseActivity
import ir.nilva.abotorab.webservices.callWebservice
import ir.nilva.abotorab.webservices.getServices
import ir.nilva.abotorab.work.DeliveryWorker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import permissions.dispatcher.*
import java.lang.Exception
import java.util.concurrent.TimeUnit

@RuntimePermissions
class MainActivity : BaseActivity() {

    @NeedsPermission(Manifest.permission.CAMERA)
    fun openCamera() {
        gotoBarcodePage(true)
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showDialogBeforeCameraPermission(request: PermissionRequest) {
        MaterialDialog(this).show {
            message(R.string.camera_permission_message)
            positiveButton(R.string.permisson_ok) { request.proceed() }
            negativeButton(R.string.permission_deny) { request.cancel() }
        }
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        toast(getString(R.string.no_camera_permission))
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        toast(getString(R.string.no_camera_permission))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCircularMenu()
        logout.setOnClickListener { logout() }
        sendCachedHashes2Server()
        fillHeader()
        val depositoryName = defaultCache()["depository"] ?: "امانت داری";
        labelTextView.text = depositoryName
    }

    private fun fillHeader() {
        CoroutineScope(Dispatchers.Main).launch {
            callWebservice {
                getServices().startReport()
            }?.run {
                cabinetCount.text = totalCabinets.toString()
                emptyCabinets.text = emptyCells.toString()
                registerCount.text = totalDeliveries.toString()
            }
        }
    }

    private fun sendCachedHashes2Server() {
        CoroutineScope(Dispatchers.Main).launch {
            val offlineDeliveries =
                AppDatabase.getInstance().offlineDeliveryDao().getAll()
            if (!offlineDeliveries.isNullOrEmpty()) {
                WorkManager.getInstance(this@MainActivity).enqueue(
                    PeriodicWorkRequestBuilder<DeliveryWorker>(30, TimeUnit.MINUTES)
                        .build()
                )
            }
        }
    }

    private fun initCircularMenu() {
        circularLayout.eventListener = object : CircleMenuView.EventListener() {
            override fun onButtonClickAnimationEnd(view: CircleMenuView, buttonIndex: Int) {
                super.onButtonClickAnimationEnd(view, buttonIndex)
                getMenuItem(buttonIndex)?.action(this@MainActivity)
            }
        }

        openMenu()
    }

    private fun openMenu() {
        Handler().postDelayed({
            circularLayout.open(true)
        }, 500)
    }

    override fun onResume() {
        super.onResume()
        openMenu()
    }

}

fun MenuItem.action(activity: MainActivity) {
    when (this) {
        MenuItem.CABINET_GIVE -> activity.openCameraWithPermissionCheck()
        MenuItem.CABINET_TAKE -> activity.gotoTakePage()
        MenuItem.CABINET_INIT -> activity.gotoCabinetListPage()
        MenuItem.CABINET_REPORT -> activity.gotoReportPage()
    }
}

fun Activity.logout() {
    MaterialDialog(this).show {
        title(text = "آیا برای خروج اطمینان دارید؟")
        positiveButton(text = "بله") {
            CoroutineScope(Dispatchers.Main).launch {
                AppDatabase.getInstance().deliveryDao().clear()
                AppDatabase.getInstance().cabinetDao().clear()
                AppDatabase.getInstance().offlineDeliveryDao().clear()
                defaultCache()["token"] = null
                gotoLoginPage()
            }
        }
        negativeButton(text = "خیر")
    }
}


