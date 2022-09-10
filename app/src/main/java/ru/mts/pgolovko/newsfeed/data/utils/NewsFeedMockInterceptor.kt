package ru.mts.pgolovko.newsfeed.data.utils

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.asResponseBody
import okhttp3.mockwebserver.MockResponse
import java.io.FileNotFoundException

class NewsFeedMockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(2000)

        val uri = chain.request().url.toUri().toString()
        val body =
            MockResponse().setBodyFromFile(
                when {
                    uri.endsWith("api/v1/getfeed") -> "assets/newsfeed.json"
                    else -> ""
                }
            ).getBody()?.asResponseBody("application/json".toMediaTypeOrNull())

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .body(body)
            .addHeader("content-type", "application/json")
            .build()
    }
}

fun MockResponse.setBodyFromFile(fileName: String): MockResponse {
    val inputStream = this::class.java.classLoader?.getResourceAsStream(fileName)
        ?: throw FileNotFoundException("File [$fileName] not found.")

    val text = inputStream.bufferedReader().readText()

    setBody(text)
    return this
}