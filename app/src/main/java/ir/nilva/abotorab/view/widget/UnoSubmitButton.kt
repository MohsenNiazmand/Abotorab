package ir.nilva.abotorab.view.widget

import android.content.Context
import android.util.AttributeSet
import ir.nilva.abotorab.R

/**
 * Created by mahdihs76 on 9/10/18.
 */
class UnoSubmitButton : UnoButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        text = context.getString(R.string.submit)
    }
}