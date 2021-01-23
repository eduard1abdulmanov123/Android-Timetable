package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import abdulmanov.eduard.timetable.domain.models.WeekType
import abdulmanov.eduard.timetable.domain.models.numberToWeekNumber
import android.content.Context
import androidx.core.content.edit

class TimetableSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setCreatorUsername(creatorUsername: String) {
        sharedPreferences.edit { putString(PREF_CREATOR_USERNAME, creatorUsername) }
    }

    fun getCreatorUsername(): String? {
        return sharedPreferences.getString(PREF_CREATOR_USERNAME, null)
    }

    fun setLink(link: String) {
        sharedPreferences.edit { putString(PREF_LINK, link) }
    }

    fun getLink(): String? {
        return sharedPreferences.getString(PREF_LINK, null)
    }

    fun setWeekType(weekType: WeekType) {
        sharedPreferences.edit { putInt(PREF_WEEK_TYPE, 0) }
    }

    fun getWeekType(): WeekType {
        return sharedPreferences.getInt(PREF_WEEK_TYPE, 0).numberToWeekNumber()
    }


    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_timetable"
        private const val PREF_CREATOR_USERNAME = "creator_username"
        private const val PREF_LINK = "link"
        private const val PREF_WEEK_TYPE = "week_type"
    }
}