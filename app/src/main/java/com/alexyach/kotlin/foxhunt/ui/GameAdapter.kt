package com.alexyach.kotlin.foxhunt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.databinding.ItemGameFieldBinding
import com.alexyach.kotlin.foxhunt.model.ModelItemField

class GameAdapter(
    private val dataList: List<ModelItemField>,
    private val listener: IClickItemAdapter
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

            if (dataField.isFox) {
                binding.field.setBackgroundResource(dataField.image)

            } else if (dataField.countFox != 0) {
                binding.field.text = dataField.countFox.toString()
                binding.field.setBackgroundResource(R.color.purple_500)

            } else {
                binding.field.text = dataField.countFox.toString()
            }

            binding.field.setOnClickListener {
                listener.clickItemAdapter(dataField)
            }
        }
    }
}
