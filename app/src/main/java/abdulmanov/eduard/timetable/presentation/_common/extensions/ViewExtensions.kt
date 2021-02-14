package abdulmanov.eduard.timetable.presentation._common.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import kotlin.reflect.KMutableProperty

fun Context.showKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.focus(show: Boolean) {
    if (show) {
        requestFocus()
        context.showKeyboard()
    } else {
        clearFocus()
        context.hideKeyboard(this)
    }
}

fun TextView.setTextColorRes(@ColorRes color:Int){
    setTextColor(ContextCompat.getColor(context, color))
}

fun Context.getScreenSize(): Point {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return Point().apply {
        wm.defaultDisplay.getSize(this)
    }
}

fun Int.dpToPx() = this * Resources.getSystem().displayMetrics.density

fun EditText.bind(property: KMutableProperty<String>) = addTextChangedListener { property.setter.call(it.toString()) }

fun CheckBox.bind(property: KMutableProperty<Boolean>) = setOnCheckedChangeListener { _, isChecked -> property.setter.call(isChecked) }