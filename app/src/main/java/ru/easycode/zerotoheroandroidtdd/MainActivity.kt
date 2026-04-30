package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as ProvideViewModel).provideViewModel(MainViewModel::class.java, this)

        viewModel.liveData().observe(this) { state ->
            state.apply(
                progressBar = binding.progressBar,
                titleTextView = binding.titleTextView,
                actionButton = binding.actionButton
            )
        }

        binding.actionButton.setOnClickListener {
            viewModel.load()
        }

        if (savedInstanceState != null) {
            viewModel.restore(BundleWrapper.Base(savedInstanceState))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.save(BundleWrapper.Base(outState))
    }
}