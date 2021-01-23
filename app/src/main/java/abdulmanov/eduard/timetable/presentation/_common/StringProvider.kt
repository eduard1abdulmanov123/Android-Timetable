package abdulmanov.eduard.timetable.presentation._common

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringProvider @Inject constructor(
    private val context: Context
) {

    fun getString(@StringRes resId: Int) = context.getString(resId)
}