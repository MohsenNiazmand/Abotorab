package ir.nilva.abotorab.view.page.main

import ir.nilva.abotorab.ApplicationContext
import ir.nilva.abotorab.R

/**
 * Created by mahdihs76 on 9/11/18.
 */

enum class MenuItem(val index: Int, val text: String) {
    CABINET_TAKE(0, ApplicationContext.context.getString(R.string.cabinet_take)),
    CABINET_REPORT(1, ApplicationContext.context.getString(R.string.report)),
    CABINET_GIVE(2, ApplicationContext.context.getString(R.string.cabinet_give)),
    CABINET_INIT(3, ApplicationContext.context.getString(R.string.init)),
}

fun getMenuItem(index: Int) = MenuItem.values().toList().find { item -> item.index == index }

