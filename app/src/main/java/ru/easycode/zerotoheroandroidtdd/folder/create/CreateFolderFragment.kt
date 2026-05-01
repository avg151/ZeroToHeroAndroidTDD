package ru.easycode.zerotoheroandroidtdd.folder.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentCreateFolderBinding

class CreateFolderFragment : Fragment() {

    private var _binding: FragmentCreateFolderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as BaseApplication
        val viewModel = app.viewModel(CreateFolderViewModel::class.java)

        binding.saveFolderButton.setOnClickListener {
            viewModel.createFolder(binding.createFolderEditText.text.toString())
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
