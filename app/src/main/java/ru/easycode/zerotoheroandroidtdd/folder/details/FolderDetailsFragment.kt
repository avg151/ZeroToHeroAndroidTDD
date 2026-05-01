package ru.easycode.zerotoheroandroidtdd.folder.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFolderDetailsBinding

class FolderDetailsFragment : Fragment() {

    private var _binding: FragmentFolderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFolderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as BaseApplication
        val viewModel = app.viewModel(FolderDetailsViewModel::class.java)

        val adapter = NotesAdapter { noteUi ->
            viewModel.editNote(noteUi)
        }
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = adapter

        app.folderLiveDataWrapper.observe().observe(viewLifecycleOwner) { folder ->
            binding.folderNameTextView.text = folder.title
            binding.notesCountTextView.text = folder.notesCount.toString()
        }

        app.noteListLiveDataWrapper.observe().observe(viewLifecycleOwner) { list ->
            adapter.update(list)
        }

        binding.addNoteButton.setOnClickListener {
            viewModel.createNote()
        }

        binding.editFolderButton.setOnClickListener {
            viewModel.editFolder()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.comeback()
            }
        })

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NotesAdapter(private val clickListener: (NoteUi) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {

    private val list = mutableListOf<NoteUi>()

    fun update(newList: List<NoteUi>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class NoteViewHolder(view: View, private val clickListener: (NoteUi) -> Unit) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.noteTitleTextView)

    fun bind(note: NoteUi) {
        title.text = note.title
        itemView.setOnClickListener { clickListener(note) }
    }
}
