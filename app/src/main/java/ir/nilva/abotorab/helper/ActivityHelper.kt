package ir.nilva.abotorab.helper

import android.app.Activity
import ir.nilva.abotorab.view.page.cabinet.CabinetActivity
import ir.nilva.abotorab.view.page.cabinet.CabinetListActivity
import ir.nilva.abotorab.view.page.cabinet.FullScreenActivity
import ir.nilva.abotorab.view.page.main.LoginActivity
import ir.nilva.abotorab.view.page.main.MainActivity
import ir.nilva.abotorab.view.page.operation.*
import org.jetbrains.anko.startActivity

/**
 * Created by mahdihs76 on 9/10/18.
 */

fun Activity.gotoMainPage() = startActivity<MainActivity>().apply { finish() }
fun Activity.gotoFullScreenPage(code: String) = startActivity<FullScreenActivity>("code" to code)
fun Activity.gotoTakePage(barcode: String = "") = startActivity<TakeActivity>("barcode" to barcode)
fun Activity.gotoGivePage(barcode: String = "") = startActivity<GiveSearchActivity>("barcode" to barcode)
fun Activity.gotoLoginPage(barcode: String = "") = startActivity<LoginActivity>("barcode" to barcode).apply { finish() }
fun Activity.gotoCabinetPage(code: String = "") = startActivity<CabinetActivity>("code" to code)
fun Activity.gotoCabinetListPage() = startActivity<CabinetListActivity>()
fun Activity.gotoReportPage() = startActivity<ReportActivity>()
fun Activity.gotoGiveSearchPage() = startActivity<GiveSearchActivity>()
fun Activity.gotoRecentGivesPage() = startActivity<RecentGivesActivity>()
fun Activity.gotoBarcodePage(isQR: Boolean) = startActivity<CameraActivity>("isQR" to isQR)



