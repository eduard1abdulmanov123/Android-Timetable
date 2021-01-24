package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import abdulmanov.eduard.timetable.domain.models.TypeWeek
import abdulmanov.eduard.timetable.domain.models.numberToTypeWeek
import android.content.Context
import androidx.core.content.edit

class TimetableSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var creatorUsername: String?
        set(value) = sharedPreferences.edit { putString(PREF_CREATOR_USERNAME, value) }
        get() = sharedPreferences.getString(PREF_CREATOR_USERNAME, null)

    var link: String?
        set(value) = sharedPreferences.edit { putString(PREF_LINK, value) }
        get() = sharedPreferences.getString(PREF_LINK, null)

    var typeWeek: TypeWeek
        set(value) = sharedPreferences.edit { putInt(PREF_TYPE_WEEK, value.number) }
        get() = sharedPreferences.getInt(PREF_TYPE_WEEK, 0).numberToTypeWeek()

    var dateUpdate: Long
        set(value) = sharedPreferences.edit { putLong(PREF_DATE_UPDATE, value) }
        get() = sharedPreferences.getLong(PREF_DATE_UPDATE, 0)


    fun clearAll() = sharedPreferences.edit { clear() }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_timetable"
        private const val PREF_CREATOR_USERNAME = "creator_username"
        private const val PREF_LINK = "link"
        private const val PREF_TYPE_WEEK = "type_week"
        private const val PREF_DATE_UPDATE = "date_update"
    }
}