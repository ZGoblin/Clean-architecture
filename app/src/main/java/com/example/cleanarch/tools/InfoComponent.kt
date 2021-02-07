package com.example.cleanarch.tools

import android.content.Context
import android.icu.text.IDNA
import com.example.cleanarch.datalayer.InfoRepository
import com.example.cleanarch.domainlayer.InfoMapper
import com.example.cleanarch.presentationlayer.InfoPresenter
import com.example.cleanarch.presentationlayer.InfoUiMapper
import com.example.cleanarch.presentationlayer.ResourceRepository
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InfoComponent {
    fun createPresenter(context: Context): InfoPresenter {
        return InfoPresenter(
            infoRepository = InfoRepository(
                multithreading = Multithreading(context),
                infoService = createService(),
                infoMapper = InfoMapper()
            ),
            infoUiMapper = InfoUiMapper(
                resourceRepository = ResourceRepository(context)
            )
        )
    }

    private fun createService(): InfoService {
        return Retrofit.Builder()
            .baseUrl("some url")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InfoService::class.java)
    }
}