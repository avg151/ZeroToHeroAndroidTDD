package ru.easycode.zerotoheroandroidtdd.folder.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditFolderBinding

class EditFolderFragment : Fragment() {

    private var _binding: FragmentEditFolderBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val FOLDER_ID_KEY = "folder_id"
        fun newInstance(folderId: Long): EditFolderFragment {
            return EditFolderFragment().apply {
                arguments = Bundle().apply {
                    putLong(FOLDER_ID_KEY, folderId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val folderId = requireArguments().getLong(FOLDER_ID_KEY)
        val app = requireActivity().application as BaseApplication
        val viewModel = app.viewModel(EditFolderViewModel::class.java)

        val folder = app.folderLiveDataWrapper.observe().value!!
        binding.folderEditText.setText(folder.title)

        binding.saveFolderButton.setOnClickListener {
            viewModel.renameFolder(folderId, binding.folderEditText.text.toString())
        }

        binding.deleteFolderButton.setOnClickListener {
            viewModel.deleteFolder(folderId)
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
