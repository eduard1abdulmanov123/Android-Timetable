package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import android.content.Context
import androidx.core.content.edit

class AuthSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var token: String?
        set(value) = sharedPreferences.edit { putString(PREF_TOKEN, value) }
        get() = sharedPreferences.getString(PREF_TOKEN, null)

    var userName: String?
        set(value) = sharedPreferences.edit { putString(PREF_USER_NAME, value) }
        get() = sharedPreferences.getString(PREF_USER_NAME, null)

    var currentTimetableId: Int?
        set(value) = sharedPreferences.edit { putInt(PREF_CURRENT_TIMETABLE_ID, value ?: -1) }
        get() = sharedPreferences.getInt(PREF_CURRENT_TIMETABLE_ID, -1).run {
            return if(this == -1) null else this
        }

    fun clearAll() = sharedPreferences.edit { clear() }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_auth"

        private const val PREF_TOKEN = "token"
        private const val PREF_USER_NAME = "user_name"
        private const val PREF_CURRENT_TIMETABLE_ID = "current_timetable_id"
    }
}