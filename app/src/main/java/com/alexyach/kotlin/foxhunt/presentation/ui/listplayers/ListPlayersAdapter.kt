package com.alexyach.kotlin.foxhunt.presentation.ui.listplayers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.databinding.ItemListPlayersBinding

class ListPlayersAdapter(private val playersList: List<UserModel>, private val currentUserName: String) :
RecyclerView.Adapter<ListPlayersAdapter.ListPlayersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlayersViewHolder {
        val binding = ItemListPlayersBinding.inflate(LayoutInflater.from(parent.context))
        return ListPlayersViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListPlayersViewHolder, position: Int) {
        holder.bind(playersList[position])
    }

    override fun getItemCount(): Int {
        return playersList.size
    }

    /** ViewHolder */
    inner class ListPlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemListPlayersBinding.bind(itemView)

        fun bind(user: UserModel) {
            if (user.name == currentUserName) {
                binding.root.setBackgroundResource(R.color.light_green)
//                binding.itemUserNameListPlayers.setTextColor(getResources().getColor(R.color.red_number, null))
            } else {
                binding.root.setBackgroundResource(0)
            }

            binding.itemUserNameListPlayers.text = user.name
            binding.itemUserNumberGameListPlayers.text = user.numberOfGame.toString()
            binding.itemUserMinNumberListPlayers.text = user.minNumberOfMoves.toString()
            binding.itemUserMaxNumberListPlayers.text = user.maxNumberOfMoves.toString()
            binding.itemUserMeanNumberListPlayers.text = String.format("%.1f", user.meanNumberOfMoves)
        }
    }
}