package com.alexyach.kotlin.foxhunt.presentation.ui.registration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.databinding.ItemUsersListBinding

class RegistrationAdapter(private val userList: List<UserModel>) :
    RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrationViewHolder {
        val binding = ItemUsersListBinding.inflate(LayoutInflater.from(parent.context))
        return RegistrationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RegistrationViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    /** ViewHolder */
    inner class RegistrationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUsersListBinding.bind(itemView)

        fun bind(user: UserModel) {
            binding.itemUserName.text = user.name
            binding.itemUserNumberGame.text = user.numberOfGame.toString()
            binding.itemUserMinNumber.text = user.minNumberOfMoves.toString()
            binding.itemUserMaxNumber.text = user.maxNumberOfMoves.toString()
            binding.itemUserMeanNumber.text = String.format("%.1f", user.meanNumberOfMoves)
        }
    }
}