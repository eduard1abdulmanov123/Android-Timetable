package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import android.content.Context
import androidx.core.content.edit

class AuthSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun setToken(token: String){
        sharedPreferences.edit { putString(PREF_TOKEN, token) }
    }

    fun getToken(): String? {
        return sharedPreferences.getString(PREF_TOKEN, null)
    }

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_auth"
        private const val PREF_TOKEN = "token"
    }
}