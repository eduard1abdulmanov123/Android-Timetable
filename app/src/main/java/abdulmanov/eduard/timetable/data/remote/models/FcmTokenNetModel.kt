package abdulmanov.eduard.timetable.data.remote.models

import com.google.gson.annotations.SerializedName

data class FcmTokenNetModel(
    @SerializedName("token")
    val token: String
)