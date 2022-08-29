package com.alexyach.kotlin.foxhunt.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    // ViewHolder
    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGameFieldBinding.bind(itemView)

        fun bind(field: ModelItemField) {
            binding.field.text = field.countFox.toString()

            binding.field.setOnClickListener {
                listener.clickItemAdapter(field)
                Log.d("myLogs", "Adapter listener: ${field.countFox}")
            }
        }
    }
}
//
//fun interface IClickItemAdapter {
//    fun clickItemAdapter(field: ModelItemField)
//}