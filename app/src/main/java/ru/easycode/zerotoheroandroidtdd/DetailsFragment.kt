package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.easycode.zerotoheroandroidtdd.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    companion object {
        private const val ITEM_ID_KEY = "itemId"
        fun newInstance(itemId: Long): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putLong(ITEM_ID_KEY, itemId)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemId = requireArguments().getLong(ITEM_ID_KEY)
        val viewModel = (requireActivity().application as TaskApp).getViewModel(
            DetailsViewModel::class.java,
            requireActivity() as MainActivity
        )

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.itemTextView.text = it
            binding.itemInputEditText.setText(it)
        }

        viewModel.init(itemId)

        binding.deleteButton.setOnClickListener {
            viewModel.delete(itemId)
        }

        binding.updateButton.setOnClickListener {
            viewModel.update(itemId, binding.itemInputEditText.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
