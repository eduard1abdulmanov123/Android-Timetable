package abdulmanov.eduard.timetable.presentation._common.extensions

fun Throwable.getStatus():String {
    return try {
        message.toString().split(":")[1].replace(" ","")
    } catch (e : Exception){
        ""
    }
}

fun Throwable.getCode(): Int {
    return try {
        message.toString().split(" ")[1].toInt()
    } catch (e : Exception) {
        -1
    }
}