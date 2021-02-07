package com.example.cleanarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cleanarch.databinding.ActivityMainBinding
import com.example.cleanarch.presentationlayer.InfoUiModel
import com.example.cleanarch.presentationlayer.InfoView
import com.example.cleanarch.tools.InfoComponent

class InfoActivity : AppCompatActivity(R.layout.activity_main), InfoView {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val presenter = InfoComponent.createPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showInfo(info: InfoUiModel) {
        binding.apply {
            titleTextView.text = info.title
            descriptionTextView.text = info.description
            titleTextView.setTextColor(info.colors.titleColor)
            descriptionTextView.setTextColor(info.colors.descriptionColor)
        }
    }

    override fun showError(error: String) {
        binding.titleTextView.text = error
    }
}