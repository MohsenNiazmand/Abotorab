package ir.nilva.abotorab.view.page.operation

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scansolutions.mrzscannerlib.MRZResultModel
import com.scansolutions.mrzscannerlib.MRZScanner
import com.scansolutions.mrzscannerlib.MRZScannerListener
import com.scansolutions.mrzscannerlib.ScannerType
import ir.nilva.abotorab.R
import ir.nilva.abotorab.view.page.base.BaseActivity

class PassportScannerActivity : BaseActivity(), MRZScannerListener {
    companion object {
        const val RESULT_OK = 1
    }

    var activityType: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport_scanner)
        activityType = intent.getIntExtra("type", 0)
        initMRZ()
    }


    private fun initMRZ() {
        val mrzScanner =
            supportFragmentManager.findFragmentById(R.id.scannerFragment) as MRZScanner?

        mrzScanner?.setScannerType(ScannerType.SCANNER_TYPE_MRZ) // Options: [SCANNER_TYPE_MRZ, SCANNER_TYPE_DOC_IMAGE_ID, SCANNER_TYPE_DOC_IMAGE_PASSPORT, TYPE_DRIVING_LICENCE]


        MRZScanner.setIDActive(true) // Enable/disable the ID document type


        MRZScanner.setPassportActive(true) // Enable/disable the Passport document type


        MRZScanner.setVisaActive(true) // Enable/disable the Visa document type


        MRZScanner.setMaxThreads(2) // Set the max CPU threads that the scanner can use


        MRZScanner.registerWithLicenseKey(
            this,
//            "0F3ECBB4BCA0EEFB6741A451B0C651C78C5C62ED6D539BDC9F8561F6439BE24925085A1D64EF0FEF2F3FD699239BAF6F12C8D7E404FB67B88B9B4052A852FE13"
            "B98B693BD419EE87F5DC7E33676FC413D3559519D770699AC7D38EAB1B45ECBD78705F76B795133F12F88B6A192C3D6C"
        ) { result, errorMessage ->
            if (errorMessage != null) {
                Log.e("MRZ RESULT", "ERROR Result code: $result. $errorMessage")
                Toast.makeText(this, "خطای سرویس اسکن پاسپورت : $errorMessage", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.i(
                    "MRZ RESULT",
                    "Result code: $result. Registration successful."
                )
            }
        }


        val sdkVersion = MRZScanner.sdkVersion() // Get the current SDK version of MRZScanner

    }

    override fun successfulScanWithResult(mrzResultModel: MRZResultModel?) {
        var intent: Intent? = null

        if (activityType == 1) {
            intent = Intent(this, GiveSearchActivity::class.java)
        } else if (activityType == 2) {
            intent = Intent(this, TakeActivity::class.java)
        }

        intent?.putExtra("firstName", mrzResultModel?.givenNamesReadable())
        intent?.putExtra("lastName", mrzResultModel?.surnamesReadable())
        intent?.putExtra("country", mrzResultModel?.issuingCountry)
        intent?.putExtra("passportId", mrzResultModel?.documentNumber)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun successfulScanWithDocumentImage(p0: Bitmap?) {
        TODO("Not yet implemented")
    }

    override fun successfulIdFrontImageScan(p0: Bitmap?, p1: Bitmap?) {
        TODO("Not yet implemented")
    }

    override fun scanImageFailed() {
        TODO("Not yet implemented")
    }

    override fun permissionsWereDenied() {
        TODO("Not yet implemented")
    }
}