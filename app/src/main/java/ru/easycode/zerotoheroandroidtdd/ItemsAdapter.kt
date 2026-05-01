package ru.easycode.zerotoheroandroidtdd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class ItemsAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items = mutableListOf<ItemUi>()

    fun update(newItems: List<ItemUi>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(
        private val binding: ItemLayoutBinding,
        private val clickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemUi) {
            binding.elementTextView.text = item.text
            binding.root.setOnClickListener {
                clickListener.click(item)
            }
        }
    }

    interface ClickListener {
        fun click(item: ItemUi)
    }
}
