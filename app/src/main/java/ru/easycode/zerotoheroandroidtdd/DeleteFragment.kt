package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDeleteBinding

class DeleteFragment : Fragment() {

    companion object {
        private const val ITEM_ID_KEY = "itemId"
        fun newInstance(itemId: Long): DeleteFragment {
            val fragment = DeleteFragment()
            val args = Bundle()
            args.putLong(ITEM_ID_KEY, itemId)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentDeleteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemId = requireArguments().getLong(ITEM_ID_KEY)
        val viewModel = (requireActivity().application as TaskApp).getViewModel(
            DeleteViewModel::class.java,
            requireActivity() as MainActivity
        )

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.itemTitleTextView.text = it
        }

        viewModel.init(itemId)

        binding.deleteButton.setOnClickListener {
            viewModel.delete(itemId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
