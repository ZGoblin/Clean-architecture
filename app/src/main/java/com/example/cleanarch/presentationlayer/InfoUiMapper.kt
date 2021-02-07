package com.example.cleanarch.presentationlayer

import com.example.cleanarch.R
import com.example.cleanarch.datalayer.InfoErrors
import com.example.cleanarch.domainlayer.InfoModel
import com.example.cleanarch.domainlayer.InfoTypes
import com.example.cleanarch.tools.Result

class InfoUiMapper(private val resourceRepository: ResourceRepository) {
    fun map(infoResult: Result<InfoModel, InfoErrors>): Result<InfoUiModel, String> {
        return infoResult.mapSuccess { info ->
            val (titleColor, descriptionColor) = when (info.infoType) {
                InfoTypes.RICH -> Pair(
                    resourceRepository.getColor(R.color.rich_title_color),
                    resourceRepository.getColor(R.color.rich_description_color)
                )
                InfoTypes.FUN -> Pair(
                    resourceRepository.getColor(R.color.rich_title_color),
                    resourceRepository.getColor(R.color.rich_description_color)
                )
                InfoTypes.NORMAL -> Pair(
                    resourceRepository.getColor(R.color.rich_title_color),
                    resourceRepository.getColor(R.color.rich_description_color)
                )
            }

            InfoUiModel(
                title = info.title,
                description = info.description,
                colors = InfoColors(
                    titleColor = titleColor,
                    descriptionColor = descriptionColor
                )
            )
        }.mapError { infoErrors ->
            val errorStringRes = when (infoErrors) {
                InfoErrors.MAIN_INFO_NOT_LOADED -> R.string.error_main_info_not_loaded
                InfoErrors.ADDITIONAL_INFO_NOT_LOADED -> R.string.error_additional_info_not_loaded
            }

            resourceRepository.getString(errorStringRes)
        }
    }
}