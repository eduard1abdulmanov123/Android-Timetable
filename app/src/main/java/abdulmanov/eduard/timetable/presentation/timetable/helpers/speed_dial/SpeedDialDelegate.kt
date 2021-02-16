package abdulmanov.eduard.timetable.presentation.timetable.helpers.speed_dial

import abdulmanov.eduard.timetable.R
import abdulmanov.eduard.timetable.domain.interactors.LawInteractor
import abdulmanov.eduard.timetable.presentation._common.provides.LawProvider
import androidx.core.content.ContextCompat
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView

object SpeedDialDelegate {

    fun addActions(lawProvider: LawProvider, speedDialView: SpeedDialView){
        speedDialView.apply {
            if(lawProvider.haveAccess()) {
                addActionItem(
                    SpeedDialActionItem.Builder(R.id.multipleClass, R.drawable.ic_multiple)
                        .setFabImageTintColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setLabel(R.string.timetable_multiple_class)
                        .setLabelColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                        .setLabelClickable(false)
                        .create()
                )

                addActionItem(
                    SpeedDialActionItem.Builder(R.id.oneTimeClass, R.drawable.ic_one_time)
                        .setFabImageTintColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setLabel(R.string.timetable_one_time_class)
                        .setLabelColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                        .setLabelClickable(false)
                        .create()
                )
            }

            addActionItem(
                SpeedDialActionItem.Builder(R.id.note, R.drawable.ic_note)
                    .setLabel(R.string.timetable_note)
                    .setLabelColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setLabelBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                    .setFabImageTintColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setLabelClickable(false)
                    .create()
            )
        }
    }
}