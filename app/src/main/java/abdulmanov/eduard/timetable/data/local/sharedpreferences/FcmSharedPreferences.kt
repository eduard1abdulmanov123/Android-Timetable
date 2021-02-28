package abdulmanov.eduard.timetable.data.local.sharedpreferences

import abdulmanov.eduard.timetable.BuildConfig
import android.content.Context
import androidx.core.content.edit

class FcmSharedPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var fcmToken: String?
        set(value) = sharedPreferences.edit { putString(PREF_TOKEN, value) }
        get() = sharedPreferences.getString(PREF_TOKEN, null)

    companion object{
        private const val PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}_fcm"

        private const val PREF_TOKEN = "token"
    }
}