package com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.foxhunt.databinding.ItemGameFieldBinding
import com.alexyach.kotlin.foxhunt.domain.model.ModelItemField
import com.alexyach.kotlin.foxhunt.domain.model.StateField

class GameAdapter(
    private val dataList: List<ModelItemField>,
    private val clickItem: IClickItemAdapter,
    private val longClickItem: ILongClickItemAdapter
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameFieldBinding.inflate(LayoutInflater.from(parent.context))
        return GameViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    /** ViewHolder */
    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGameFieldBinding.bind(itemView)

        fun bind(dataField: ModelItemField) {


            if (dataField.markerNotFox) {
                binding.imgNotFox.visibility = View.VISIBLE
            } else {
                binding.imgNotFox.visibility = View.GONE
            }

            when (dataField.viewMode) {

                StateField.NO_CLICK -> {
                    binding.field.setBackgroundResource(dataField.image)
                    binding.field.text = ""
                }
                StateField.SIT_FOX -> {
                    binding.field.setBackgroundResource(dataField.image)
                    binding.imgNotFox.visibility = View.GONE
                }
                StateField.COUNT_FOX -> {
                    binding.field.text = dataField.countFox.toString()
                    binding.field.setBackgroundResource(0)
                    binding.imgNotFox.visibility = View.GONE
                }
            }

            // Listener Click
            binding.field.setOnClickListener {
                clickItem.clickItemAdapter(adapterPosition)
            }

            // Listener Long Click
            binding.field.setOnLongClickListener {
                longClickItem.longClickItem(adapterPosition)
                true
            }

        }
    }
}
