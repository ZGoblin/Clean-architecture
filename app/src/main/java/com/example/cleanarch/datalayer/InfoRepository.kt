package com.example.cleanarch.datalayer

import com.example.cleanarch.domainlayer.InfoMapper
import com.example.cleanarch.domainlayer.InfoModel
import com.example.cleanarch.tools.AsyncOperation
import com.example.cleanarch.tools.InfoService
import com.example.cleanarch.tools.Multithreading
import com.example.cleanarch.tools.Result

enum class InfoErrors {
    MAIN_INFO_NOT_LOADED,
    ADDITIONAL_INFO_NOT_LOADED
}

class InfoRepository(
    private val multithreading: Multithreading,
    private val infoService: InfoService,
    private val infoMapper: InfoMapper
) {
    fun getInfo(): AsyncOperation<Result<InfoModel, InfoErrors>> {
        val asyncOperation = multithreading.async<Result<Info, InfoErrors>> {
            val firstInfo = infoService.getInfo().execute().body()
                ?: return@async Result.error(InfoErrors.MAIN_INFO_NOT_LOADED)

            val additionalInfoResponse = infoService.getInfo().execute()

            if (!additionalInfoResponse.isSuccessful) {
                return@async Result.error(InfoErrors.ADDITIONAL_INFO_NOT_LOADED)
            }

            val additionalDescription = additionalInfoResponse.body()?.description ?: ""
            val description = firstInfo.description + "\n" + additionalDescription

            return@async Result.success(
                Info(
                    title = firstInfo.title,
                    description = description
                )
            )
        }

        return asyncOperation.map(infoMapper::map)

    }
}