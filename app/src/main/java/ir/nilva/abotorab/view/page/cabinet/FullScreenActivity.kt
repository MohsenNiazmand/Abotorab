package ir.nilva.abotorab.view.page.cabinet

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import ir.nilva.abotorab.R
import ir.nilva.abotorab.db.AppDatabase
import ir.nilva.abotorab.helper.getImageResource
import ir.nilva.abotorab.model.Row
import ir.nilva.abotorab.view.page.base.BaseActivity
import kotlinx.android.synthetic.main.activity_full_screen.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.margin
import java.util.*


const val DEFAULT_CABINET_MARGIN = 10

class FullScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor(Color.WHITE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        val code = intent?.extras?.getString("code")
        AppDatabase.getInstance()
            .cabinetDao().getLiveData(code!!).observe(this, Observer {
                drawCabinet(it.rows)
            })
    }

    private fun drawCabinet(rows: ArrayList<Row>) {
        val rowsCount = rows.size
        val columnsCount = rows[0].cells.size

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels

        rows.forEach {
            val eachWidth = screenWidth / columnsCount
            val eachHeight = screenHeight / rowsCount

            val layout = LinearLayout(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    screenWidth,
                    eachHeight - 2 * DEFAULT_CABINET_MARGIN
                )
            }

            it.cells.forEach {
                val imageView = ImageView(this).apply {
                    imageResource = it.getImageResource(false)

                    layoutParams = LinearLayout.LayoutParams(
                        eachWidth - 2 * DEFAULT_CABINET_MARGIN,
                        eachHeight - 2 * DEFAULT_CABINET_MARGIN
                    ).apply {
                        margin = DEFAULT_CABINET_MARGIN
                    }
                }

                layout.addView(imageView)
            }

            rootLayout.addView(layout)
        }

    }
}
