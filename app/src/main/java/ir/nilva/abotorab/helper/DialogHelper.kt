package ir.nilva.abotorab.helper

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import ir.nilva.abotorab.R
import ir.nilva.abotorab.model.DeliveryResponse
import kotlinx.android.synthetic.main.item_pilgirim.view.*
import org.jetbrains.anko.layoutInflater

fun Context.showResult(actionLabel: String, list: List<DeliveryResponse>?, click: (hashId: String) -> Unit) {
    if (list == null || list.isEmpty()) {
        toastWarning("هیچ موردی یافت نشد")
        return
    }
    val view = LinearLayout(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        orientation = LinearLayout.VERTICAL
    }
    val dialog = MaterialDialog(this)
    list.forEach { item ->
        view.addView(layoutInflater.inflate(R.layout.item_pilgirim, null).apply {
            var cabinetCode = ""
            val packs = item.pack
            if (packs.isNotEmpty()) cabinetCode = packs[0].cell
            title.text = item.pilgrim.country + " از " + item.pilgrim.name
            phoneNumber.text = "شماره تلفن:" + item.pilgrim.phone
            cabinet.text = " شماره قفسه:${mapCabinetLabelWithCab(cabinetCode)}"
            subTitle.text = "زمان تحویل:" + item.enteredAt
            button.text = actionLabel
            button.setOnClickListener {
                dialog.dismiss()
                click(item.hashId)
            }
        })
    }
    dialog.customView(view = view, scrollable = true).show()
}