package com.example.cleanarch.presentationlayer

import com.example.cleanarch.datalayer.InfoRepository
import com.example.cleanarch.tools.CancelableOperation
import com.example.cleanarch.tools.Result

interface InfoView {
    fun showInfo(info: InfoUiModel)
    fun showError(error: String)
}

class InfoPresenter(
    private val infoRepository: InfoRepository,
    private val infoUiMapper: InfoUiMapper
) {
    private var view: InfoView? = null
    private var cancelableOparation: CancelableOperation? = null

    fun attachView(infoView: InfoView) {
        view = infoView

        cancelableOparation = infoRepository.getInfo()
            .map(infoUiMapper::map)
            .postOnMainThread(::showResult)
    }

    fun detachView() {
        view = null
        cancelableOparation?.cancel()
    }

    private fun showResult(result: Result<InfoUiModel, String>) {
        if (result.isError) {
            view?.showError(result.errorResult)
        } else {
            view?.showInfo(result.successResult)
        }
    }
}