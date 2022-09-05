package ir.nilva.abotorab.view.page.operation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.db.model.DeliveryEntity
import ir.nilva.abotorab.db.model.OfflineDeliveryEntity
import ir.nilva.abotorab.helper.getCountries
import ir.nilva.abotorab.helper.showResult
import ir.nilva.abotorab.helper.toastError
import ir.nilva.abotorab.helper.toastSuccess
import ir.nilva.abotorab.view.page.base.BaseActivity
import ir.nilva.abotorab.webservices.callWebservice
import ir.nilva.abotorab.webservices.callWebserviceWithFailure
import ir.nilva.abotorab.webservices.getServices
import kotlinx.android.synthetic.main.activity_give.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GiveSearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_give)
        search.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                search.visibility = View.INVISIBLE
                spinKit.visibility = View.VISIBLE
                callWebservice {
                    getServices().deliveryList(
                        firstName.text.toString(),
                        lastName.text.toString(),
                        country.text.toString(),
                        phone.text.toString(),
                        passportId.text.toString(),
                        true
                    )
                }?.run {
                    showResult("تحویل", this) { hashId ->
                        MaterialDialog(this@GiveSearchActivity).show {
                            title(text = "در صورتی که زائر رسید خود را تحویل نداده است، فرم تعهدنامه کتبی به همراه امضا از ایشان دریافت کنید")
                            positiveButton(text = "انجام شد") {
                                callGiveWS(hashId)
                            }
                            negativeButton(text = "بعدا") {
                            }
                        }
                    }
                }
                search.visibility = View.VISIBLE
                spinKit.visibility = View.INVISIBLE
            }
        }

        country.threshold = 1
        country.setAdapter(CountryAdapter(this, R.layout.item_country, ArrayList(getCountries())))

        fab.setOnClickListener {
            startScanning()
        }


    }


    private fun startScanning() {
        startActivityForResult(Intent(this, PassportScannerActivity::class.java).apply {
            putExtra("type",1)
        }, 100)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

        if (requestCode == 100) {
            if (resultCode == PassportScannerActivity.RESULT_OK && data != null) {
                firstName.text = data.getStringExtra("firstName")!!.toEditable()
                lastName.text = data.getStringExtra("lastName")!!.toEditable()
                country.text = data.getStringExtra("country")!!.toEditable()
                passportId.text = data.getStringExtra("passportId")!!.toEditable()
            }
        }
    }

}


fun Context.callGiveWS(hashId: String, cellCode: String = "") =
    CoroutineScope(Dispatchers.Main).launch {
        callWebserviceWithFailure({ getServices().give(hashId) }) { response, code ->
            if (code != 400) {
                toastSuccess("پس از برقراری ارتباط با سرور گزارش میشود")
                cacheHashId(hashId)
            } else {
                toastError(response)
            }
        }?.run {
            toastSuccess("محموله با موفقیت تحویل داده شد")
            AppDatabase.getInstance().deliveryDao().insertAndDeleteOther(
                DeliveryEntity(
                    nickname = pilgrim.name,
                    country = pilgrim.country,
                    cellCode = this.cellCode,
                    phone = pilgrim.phone,
                    exitedAt = exitAt,
                    hashId = hashId
                )
            )
        }
    }

private fun cacheHashId(hashId: String) = CoroutineScope(Dispatchers.IO).launch {
    AppDatabase.getInstance().offlineDeliveryDao().insert(OfflineDeliveryEntity(hashId))
}

