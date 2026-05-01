package ru.easycode.zerotoheroandroidtdd.note.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentEditNoteBinding

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val NOTE_ID_KEY = "note_id"
        fun newInstance(noteId: Long): EditNoteFragment {
            return EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(NOTE_ID_KEY, noteId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val noteId = requireArguments().getLong(NOTE_ID_KEY)
        val app = requireActivity().application as BaseApplication
        val viewModel = app.viewModel(EditNoteViewModel::class.java)

        app.noteLiveDataWrapper.observe().observe(viewLifecycleOwner) { text ->
            binding.noteEditText.setText(text)
        }

        binding.saveNoteButton.setOnClickListener {
            viewModel.renameNote(noteId, binding.noteEditText.text.toString())
        }

        binding.deleteNoteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        })

        viewModel.init(noteId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
