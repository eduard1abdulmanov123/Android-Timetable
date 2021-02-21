package abdulmanov.eduard.timetable.presentation._common.extensions

fun Throwable.getStatus():String{
    val messageStr = message.toString()

    return message.toString().split(":")[1].replace(" ","")
}