package com.example.cleanarch.domainlayer

import com.example.cleanarch.datalayer.Info
import com.example.cleanarch.datalayer.InfoErrors
import com.example.cleanarch.tools.Result

enum class InfoTypes {
    RICH,
    NORMAL,
    FUN
}

class InfoMapper {
    companion object {
        private const val FUN_ENDING = "FUN"
        private const val RICH_DESCRIPTION_CHAR_LIMIT = 300
        private const val LETTER_TITLE = "Letter"
        private const val LETTER_ENDING = "From PM Tech"
        private const val REMOVE_DESCRIPTION_END_TITLE_SYMBOL = 'A'
        private const val REMOVE_DESCRIPTION_START_TITLE_SYMBOL = 'X'
        private const val END_SYMBOLS_TO_REMOVE = 100
        private const val START_SYMBOLS_TO_REMOVE = 15
    }

    fun map(infoResult: Result<Info, InfoErrors>): Result<InfoModel, InfoErrors> {
        return infoResult.mapSuccess { info ->
            val updateDescription = when {
                info.title == LETTER_TITLE -> {
                    info.description + LETTER_ENDING
                }
                info.title.firstOrNull() == REMOVE_DESCRIPTION_END_TITLE_SYMBOL -> {
                    info.description.removeRange(
                        info.description.length - END_SYMBOLS_TO_REMOVE,
                        info.description.length
                    )
                }
                info.title.firstOrNull() == REMOVE_DESCRIPTION_START_TITLE_SYMBOL -> {
                    info.description.removeRange(0, START_SYMBOLS_TO_REMOVE)
                }
                else -> info.description
            }

            val infoType = when {
                info.title.endsWith(FUN_ENDING) -> InfoTypes.FUN
                info.description.length > RICH_DESCRIPTION_CHAR_LIMIT -> InfoTypes.RICH
                else -> InfoTypes.NORMAL
            }

            InfoModel(
                title = info.title,
                description = updateDescription,
                infoType = infoType
            )
        }
    }
}