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
        val adapter = ItemsAdapter()
        binding.recyclerView.adapter = adapter
        
        val dao = (requireActivity().application as TaskApp).database.itemsDao()
        adapter.update(dao.list())

        binding.addButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToAdd()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}