package abdulmanov.eduard.timetable.data.remote.helpers

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class ErrorInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(response.code == 400 && response.body != null) {
            val responseBody = response.body!!
            val jsonString = responseBody.string()
            val jsonObject = JSONObject(jsonString)

            return if(jsonObject.has("status") && jsonObject.has("message")){
                val jsonMessage = jsonObject.getString("message")
                val jsonStatus = jsonObject.getString("status")
                val message = ":$jsonStatus:$jsonMessage"

                response.newBuilder()
                    .code(400)
                    .body(jsonString.toResponseBody(responseBody.contentType()))
                    .message(message)
                    .build()
            } else {
                response.newBuilder()
                    .body(jsonString.toResponseBody(responseBody.contentType()))
                    .build()
            }
        }

        return response
    }
}