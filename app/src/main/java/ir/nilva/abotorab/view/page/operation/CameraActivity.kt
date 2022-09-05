package ir.nilva.abotorab.view.page.operation

import android.graphics.PointF
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import ir.nilva.abotorab.R
import ir.nilva.abotorab.helper.*
import ir.nilva.abotorab.view.page.base.BaseActivity
import kotlinx.android.synthetic.main.activity_barcode.*
import kotlinx.android.synthetic.main.give_verification.view.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange


class CameraActivity : BaseActivity(), QRCodeReaderView.OnQRCodeReadListener {

    private var isQR = false
    private var mostRecentHashId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        confirm_cb.isChecked = defaultCache()["need_to_confirmation"] ?: true

        confirm_cb.onCheckedChange { _, isChecked ->
            defaultCache()["need_to_confirmation"] = isChecked
        }

        confirm_layout.setOnClickListener { confirm_cb.performClick() }
        recent_layout.setOnClickListener { recent.performClick() }
        search_layout.setOnClickListener { search.performClick() }

        search.setOnClickListener { gotoGiveSearchPage() }
        recent.setOnClickListener { gotoRecentGivesPage() }

        qrView.setOnQRCodeReadListener(this)

        qrView.setQRDecodingEnabled(true)
        qrView.setTorchEnabled(true)

        isQR = intent?.extras?.getBoolean("isQR") ?: false
    }

    var isBarcodeFound = false

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        if (!isBarcodeFound) {
            isBarcodeFound = true
            val result = text ?: ""
            give(result)
        }
    }

    private fun give(barcode: String) {
        val map: HashMap<Int, String> = hashMapOf(0 to "A", 1 to "B", 2 to "C", 3 to "D", 4 to "E", 5 to "F", 6 to "G", 7 to "H", 8 to "I",9 to "J")
        var label=""
        if (barcode.isNotEmpty()) {
            try {
                val text = String(
                    Base64.decode(
                        barcode,
                        Base64.DEFAULT
                    ),
                    Charsets.UTF_8
                ).substringAfter("http://gbaghiyatallah.ir/?data=")
                    .split("#")



                for (i in map.keys){
                    if (text[4].substring(2,3).toInt()==(map.keys.indexOf(i))){
                        print(map[i])
                        label=text[4].substring(3,4)+(map[i])+text[4].substring(0,2)
                    }
                }

                Log.i("TEEEXT", text.toString())
                val hashId = text[2]
                val view = layoutInflater.inflate(R.layout.give_verification, null)
                view.nickName.text = text[0]
                view.county.text = "از کشور ${text[1]}"
                val phoneNumber4Digit = text[3]
                if (phoneNumber4Digit.isEmpty()) {
                    view.phoneNumber.visibility = View.GONE
                } else {
                    view.phoneNumber.text = "شماره تلفن‌ : $phoneNumber4Digit********"
                }
//                view.cellCode.text = "شماره قفسه : ${mapCabinetLabelWithCab(text[4])}"
                view.cellCode.text = "شماره قفسه : ${label}"

                confirmToGive(view, hashId, text[4])
            } catch (e: Exception) {
                toastError("بارکد اسکن شده معتبر نمی‌باشد")
            }
        }
    }

    private fun confirmToGive(view: View?, hashId: String, cellCode: String) {
        if (mostRecentHashId == hashId) {
            isBarcodeFound = false
            return
        }
        val need = /*defaultCache()["need_to_confirmation"] ?:*/ true
        if (need) {
            MaterialDialog(this).show {
                customView(view = view)
                title(text = "تایید")
                positiveButton(text = "بله") {
                    callGiveWS(hashId, cellCode)
                    mostRecentHashId = hashId
                    isBarcodeFound = false

                }
                negativeButton(text = "خیر") {
                    isBarcodeFound = false
                    mostRecentHashId = null
                }
            }
        } else {
            callGiveWS(hashId, cellCode)
            mostRecentHashId = hashId
            isBarcodeFound = false
        }
    }

    override fun onResume() {
        super.onResume()
        qrView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        isBarcodeFound = false
        mostRecentHashId = null
        qrView.stopCamera()
    }

}
