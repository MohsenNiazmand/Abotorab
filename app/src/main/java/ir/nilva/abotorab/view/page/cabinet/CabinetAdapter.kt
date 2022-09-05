package ir.nilva.abotorab.view.page.cabinet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import ir.nilva.abotorab.R
import ir.nilva.abotorab.helper.*
import ir.nilva.abotorab.model.CabinetResponse
import kotlinx.android.synthetic.main.activity_cabinet.*
import kotlinx.android.synthetic.main.activity_cabinet.view.*
import kotlinx.android.synthetic.main.cabinet.view.*
import org.json.JSONObject


class CabinetAdapter(
    val activity: Activity,
    var cabinet: CabinetResponse?,
    var rows: Int,
    var columns: Int,
    var carriageEnabled: Boolean,
    var rowDir: Boolean,
    var colDir: Boolean
) : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View =
        LayoutInflater.from(activity).inflate(R.layout.cabinet, null).apply {
            if (columns > 6) {
                image.requestLayout()
                image.layoutParams.width = activity.getScreenWidth() / 7
            }

            if (carriageEnabled && p0 / columns == rows - 1) {
                long_image.visibility = View.VISIBLE
                long_image.requestLayout()
                long_image.layoutParams.width = 200
//                long_image.layoutParams.width = (activity.getScreenWidth() / columns)
                long_image.layoutParams.height = (long_image.layoutParams.width * 1.3).toInt()
                image.visibility = View.GONE
            }
            cabinet ?: return@apply
            val cell = cabinet?.getCell(p0, rowDir, colDir)
            cell ?: return@apply
            if (cell.size > 0) {
                long_image.visibility = View.VISIBLE
                long_image.requestLayout()
                long_image.layoutParams.width = 200
//                long_image.layoutParams.width = (activity.getScreenWidth() / columns)
                long_image.layoutParams.height = (long_image.layoutParams.width * 1.3).toInt()
                image.visibility = View.GONE
            }
            val needLongCell = carriageEnabled && p0 / columns == rows - 1
            if (needLongCell || cell.size > 0) {
                long_image.setImageResource(cell.getImageResource(true))
            } else {
                image.setImageResource(cell.getImageResource(false))
            }
            codeTextView.visibility = View.VISIBLE
            codeTextView.text = mapCabinetLabel(cell.code)

        }

    override fun getItem(p0: Int) = null

    override fun getItemId(p0: Int): Long = System.currentTimeMillis()

    override fun getCount() = rows * columns

}