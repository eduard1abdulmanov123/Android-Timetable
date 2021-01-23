package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import android.content.Context
import androidx.core.content.edit

class AuthSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setToken(token: String?){
        sharedPreferences.edit { putString(PREF_TOKEN, token) }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(PREF_TOKEN, null)
    }

    fun setUserName(userName: String?) {
        sharedPreferences.edit { putString(PREF_USER_NAME, userName) }
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(PREF_USER_NAME, null)
    }

    fun setCurrentTimetableId(currentTimetableId: Int?) {
        sharedPreferences.edit { putInt(PREF_CURRENT_TIMETABLE_ID, currentTimetableId ?: -1) }
    }

    fun getCurrentTimetableId(): Int? {
        val currentTimetableId = sharedPreferences.getInt(PREF_CURRENT_TIMETABLE_ID, -1)

        return if(currentTimetableId == -1) {
            null
        } else {
            currentTimetableId
        }
    }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_auth"
        private const val PREF_TOKEN = "token"
        private const val PREF_USER_NAME = "user_name"
        private const val PREF_CURRENT_TIMETABLE_ID = "current_timetable_id"
    }
}