package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (requireActivity().application as TaskApp).getViewModel(
            MainViewModel::class.java,
            requireActivity() as MainActivity
        )

        val adapter = ItemsAdapter(object : ItemsAdapter.ClickListener {
            override fun click(item: ItemUi) {
                (requireActivity() as MainActivity).navigateToDelete(item.id)
            }
        })
        binding.recyclerView.adapter = adapter

        viewModel.init()

        (requireActivity().application as TaskApp).liveDataWrapper.liveData()
            .observe(viewLifecycleOwner) {
                adapter.update(it)
            }

        binding.addButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToAdd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
