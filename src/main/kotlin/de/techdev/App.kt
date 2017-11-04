package de.techdev

import okhttp3.OkHttpClient
import okhttp3.Request

class App {

    fun start() {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://techdev.de").build()

        client.newCall(request).execute()
    }

}
