package ru.easycode.zerotoheroandroidtdd.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.ProvideViewModel
import ru.easycode.zerotoheroandroidtdd.core.ViewModelFactory
import ru.easycode.zerotoheroandroidtdd.create.CreateScreen
import ru.easycode.zerotoheroandroidtdd.create.CreateViewModel
import ru.easycode.zerotoheroandroidtdd.databinding.ActivityMainBinding
import ru.easycode.zerotoheroandroidtdd.list.BundleWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListLiveDataWrapper
import ru.easycode.zerotoheroandroidtdd.list.ListScreen
import ru.easycode.zerotoheroandroidtdd.list.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory

    private lateinit var listView: View
    private lateinit var createView: View

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.listFrameLayout
        createView = binding.createFrameLayout

        val navigation = Navigation.Base()
        val listLiveDataWrapper = ListLiveDataWrapper.Base()

        val provideViewModel = object : ProvideViewModel {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T {
                return when (viewModelClass) {
                    MainViewModel::class.java -> MainViewModel(navigation)
                    ListViewModel::class.java -> ListViewModel(listLiveDataWrapper, navigation)
                    CreateViewModel::class.java -> CreateViewModel(
                        listLiveDataWrapper,
                        navigation,
                        factory
                    )
                    else -> throw IllegalArgumentException("Unknown ViewModel class: $viewModelClass")
                } as T
            }
        }
        factory = ViewModelFactory.Base(provideViewModel)

        val mainViewModel = factory.viewModel(MainViewModel::class.java)
        val listViewModel = factory.viewModel(ListViewModel::class.java)
        val createViewModel = factory.viewModel(CreateViewModel::class.java)

        val items = mutableListOf<CharSequence>()
        val adapter = MyAdapter(items)
        binding.recyclerView.adapter = adapter

        mainViewModel.navigationLiveData().observe(this) { screen ->
            showScreen(screen)
        }

        listViewModel.liveData().observe(this) { newList ->
            items.clear()
            items.addAll(newList)
            adapter.notifyDataSetChanged()
        }

        binding.addButton.setOnClickListener {
            listViewModel.create()
        }

        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.createButton.isEnabled = (s?.length ?: 0) >= 3
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.createButton.isEnabled = false
        binding.createButton.setOnClickListener {
            createViewModel.add(binding.inputEditText.text.toString())
            binding.inputEditText.setText("")
        }

        if (savedInstanceState == null) {
            mainViewModel.init(true)
        } else {
            mainViewModel.init(false)
            listViewModel.restore(BundleWrapper.Base(savedInstanceState))
            val screenName = savedInstanceState.getString(SCREEN_KEY)
            if (screenName == CreateScreen::class.java.simpleName) {
                showScreen(CreateScreen)
            } else {
                showScreen(ListScreen)
            }
        }
    }

    private fun showScreen(screen: Screen) {
        binding.rootLayout.removeAllViews()
        when (screen) {
            is ListScreen -> binding.rootLayout.addView(listView)
            is CreateScreen -> binding.rootLayout.addView(createView)
            is Screen.Pop -> binding.rootLayout.addView(listView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        factory.viewModel(ListViewModel::class.java).save(BundleWrapper.Base(outState))
        val currentScreenName = if (createView.parent != null) {
            CreateScreen::class.java.simpleName
        } else {
            ListScreen::class.java.simpleName
        }
        outState.putString(SCREEN_KEY, currentScreenName)
    }

    override fun onBackPressed() {
        if (createView.parent != null) {
            factory.viewModel(CreateViewModel::class.java).comeback()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val SCREEN_KEY = "screen_key"
    }
}

class MyAdapter(private val items: List<CharSequence>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val textView = view.findViewById<TextView>(R.id.elementTextView)

    fun bind(text: CharSequence) {
        textView.text = text
    }
}
