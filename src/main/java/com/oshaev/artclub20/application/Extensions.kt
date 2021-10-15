package com.oshaev.artclub20.application


import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.DimenRes

fun deleteDashes(str: String): String = str.replace("-", "")

fun View.makeVisibleOrGone(isVisible: Boolean): View {
    if (isVisible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
    return this
}

fun CharSequence?.stringOrNull(): String {
    return this.toString() ?: "null"
}

fun CharSequence?.isNotNull(): Boolean {
    return !this.isNullOrEmpty()
}

fun View.makeVisible(): View {
    this.visibility = View.VISIBLE
    return this
}

fun View.makeGone(): View {
    this.visibility = View.GONE
    return this
}

fun getDimen(
    @DimenRes
    id: Int
): Int = ArtClubApplication.appContext.resources.getDimensionPixelSize(id)


fun View.setMargin(
    horizontal: Int? = null,
    vertical: Int? = null,
    left: Int? = horizontal,
    top: Int? = vertical,
    right: Int? = horizontal,
    bottom: Int? = vertical
) {
    val lp = layoutParams as ViewGroup.MarginLayoutParams
    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.bottomMargin
    )
    requestLayout()
}

inline fun <reified R> Any.instanceIf(): R? {
    return if (this is R) this else null
} // встроенная функция, проверяющая

fun String.removeBranches(): String { // функция для того, чтобы убрать квадратные скобки из строки
    return this.replace("[", "").replace("]", "")


}



