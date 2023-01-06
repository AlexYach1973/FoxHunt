package com.alexyach.kotlin.foxhunt.presentation.ui.listplayers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.databinding.FragmentListPlayersBinding
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse
import com.alexyach.kotlin.foxhunt.presentation.ui.base.BaseFragment
import com.alexyach.kotlin.foxhunt.utils.KEY_USER_NAME
import com.alexyach.kotlin.foxhunt.utils.NAME_UNKNOWN

class ListPlayersFragment : BaseFragment<FragmentListPlayersBinding,
        ListPlayersViewModel>() {

    override val viewModel: ListPlayersViewModel by lazy {
        ViewModelProvider(this)[ListPlayersViewModel::class.java]
    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentListPlayersBinding.inflate(inflater, container, false)

    private lateinit var adapter: ListPlayersAdapter
    private lateinit var userName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = arguments?.run {
            getString(KEY_USER_NAME, NAME_UNKNOWN)
        } ?: NAME_UNKNOWN

        viewModel.getStateResponse().observe(viewLifecycleOwner){state ->
            renderingResponse(state)
        }

        clickSortedPlayers()

    }

    private fun clickSortedPlayers() {
        binding.playerName.setOnClickListener {
            viewModel.readAllUsersSorted("name")
        }
        binding.playerNumberGame.setOnClickListener {
            viewModel.readAllUsersSorted("numberOfGame")
        }
        binding.playerMinNumber.setOnClickListener {
            viewModel.readAllUsersSorted("minNumberOfMoves")
        }
        binding.playerMaxNumber.setOnClickListener {
            viewModel.readAllUsersSorted("maxNumberOfMoves")
        }
        binding.playerMeanNumber.setOnClickListener {
            viewModel.readAllUsersSorted("meanNumberOfMoves")
        }

    }

    private fun renderingResponse(state: StateResponse) {
        when(state) {
            is StateResponse.SuccessResponse -> showUserList(state.userList)
            is StateResponse.ErrorResponse -> showError()
            StateResponse.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.loadingListPlayers.visibility = View.VISIBLE
    }

    private fun showUserList(userList: List<UserModel>) {
        binding.loadingListPlayers.visibility = View.GONE
        setAdapter(userList)
    }

    private fun showError() {
        binding.loadingListPlayers.visibility = View.GONE
        TODO()
    }

    private fun setAdapter(playersList: List<UserModel>) {
        adapter = ListPlayersAdapter(playersList, userName)
        binding.recyclerListPlayers.adapter = adapter
    }


    companion object {
        fun newInstance(name: String) : ListPlayersFragment {
            return ListPlayersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_USER_NAME, name)
                }
            }
        }
    }
}