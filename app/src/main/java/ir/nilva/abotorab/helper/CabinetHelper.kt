package ir.nilva.abotorab.helper

import ir.nilva.abotorab.R
import ir.nilva.abotorab.model.CabinetResponse
import ir.nilva.abotorab.model.Cell

fun CabinetResponse.getCell(index: Int, rowDir: Boolean = false, colDir: Boolean = false): Cell {
    var rowIndex = getRowsNumber() - index / getColumnsNumber() - 1
    var columnIndex = index % getColumnsNumber()
    if (rowDir)
        rowIndex = index / getColumnsNumber()
    if (colDir)
        columnIndex = getColumnsNumber() - index % getColumnsNumber() - 1
//    when (dir) {
//        1 -> {
//            rowIndex = getRowsNumber() - index / getColumnsNumber() - 1
//            columnIndex = index % getColumnsNumber()
//        }
//        2 -> {
//            rowIndex = getRowsNumber() - index / getColumnsNumber() - 1
//            columnIndex = getColumnsNumber() - index % getColumnsNumber() - 1
//        }
//        3 -> {
//            rowIndex = index / getColumnsNumber()
//            columnIndex = getColumnsNumber() - index % getColumnsNumber() - 1
//        }
//    }
    return rows[rowIndex].cells[columnIndex]
}

fun CabinetResponse.rotateRow() {
    for (row in rows)
        row.cells.reverse()
}

fun CabinetResponse.rotateCol() {
    rows.reverse()
}


fun CabinetResponse.rotate(dir: Int = 0) {
    if (dir % 2 == 0)
        rows.reverse()
    else
        for (row in rows) {
            row.cells.reverse()
        }
}

fun Cell.getIndex() = ((id % 100) / 10) * size + (id % 10)

fun CabinetResponse.getRowsNumber() = rows.size

fun CabinetResponse.getColumnsNumber() = rows[0].cells.size

fun Cell.getImageResource(longCell: Boolean) = if (longCell) when {
    !isHealthy -> R.mipmap.broken_long
    age == -1 && !isFavorite -> R.mipmap.cabinet_empty_long
    age == -1 && isFavorite -> R.mipmap.cabinet_empty_fav_long
    age == 0 && !isFavorite -> R.mipmap.cabinet_fill_1_long
    age == 0 && isFavorite -> R.mipmap.cabinet_fill_1_fav_long
    !isFavorite -> R.mipmap.cabinet_fill_3_long
    else -> R.mipmap.cabinet_fill_3_fav
} else when {
    !isHealthy -> R.mipmap.broken
    age == -1 && !isFavorite -> R.mipmap.cabinet_empty
    age == -1 && isFavorite -> R.mipmap.cabinet_empty_fav
    age == 0 && !isFavorite -> R.mipmap.cabinet_fill_1
    age == 0 && isFavorite -> R.mipmap.cabinet_fill_1_fav
    !isFavorite -> R.mipmap.cabinet_fill_3
    else -> R.mipmap.cabinet_fill_3_fav
}