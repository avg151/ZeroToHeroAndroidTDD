package ru.easycode.zerotoheroandroidtdd.folder.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.R
import ru.easycode.zerotoheroandroidtdd.core.BaseApplication
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentFoldersListBinding

class FoldersListFragment : Fragment() {

    private var _binding: FragmentFoldersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoldersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = requireActivity().application as BaseApplication
        val viewModel = app.viewModel(FolderListViewModel::class.java)

        val adapter = FoldersAdapter { folderUi ->
            viewModel.folderDetails(folderUi)
        }
        binding.foldersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.foldersRecyclerView.adapter = adapter

        app.folderListLiveDataWrapper.observe().observe(viewLifecycleOwner) { list ->
            adapter.update(list)
        }

        binding.addButton.setOnClickListener {
            viewModel.addFolder()
        }

        viewModel.init()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class FoldersAdapter(private val clickListener: (FolderUi) -> Unit) : RecyclerView.Adapter<FolderViewHolder>() {

    private val list = mutableListOf<FolderUi>()

    fun update(newList: List<FolderUi>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

class FolderViewHolder(view: View, private val clickListener: (FolderUi) -> Unit) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.folderTitleTextView)
    private val count = view.findViewById<TextView>(R.id.folderCountTextView)

    fun bind(folder: FolderUi) {
        title.text = folder.title
        count.text = folder.notesCount.toString()
        itemView.setOnClickListener { clickListener(folder) }
    }
}
