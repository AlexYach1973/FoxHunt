package com.alexyach.kotlin.foxhunt.presentation.ui.registration

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.databinding.FragmentRegistrationBinding
import com.alexyach.kotlin.foxhunt.presentation.ui.StateResponse

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
    get() = _binding!!

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    private lateinit var adapter: RegistrationAdapter

    private var newUser = UserModel(
        name = "",
        numberOfGame = 0,
        minNumberOfMoves = 0,
        maxNumberOfMoves = 0,
        sumNumberOfMoves = 0,
        meanNumberOfMoves = 0.0
    )

    private var userList: List<UserModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getStateResponse().observe(viewLifecycleOwner){ state ->
            renderingResponse(state)
        }

        // Button Enter name
        binding.btnEnterName.setOnClickListener {
            val name = binding.etEnterName.text.toString()

            if (validateEnteredName(name)) {
                newUser.name = name
                viewModel.saveUserNameToDataStore(newUser)
                // Одразу зберігаємо нового Usera в AWS
                viewModel.saveNewUserToAWS(newUser)
                returnToGameFragment()
            }
        }
    }

    private fun validateEnteredName(name: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(requireContext(), "Пусте поле", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userList.isEmpty()) {
            Toast.makeText(requireContext(), "Пустий список ігроків - Ви перший", Toast.LENGTH_SHORT).show()
            return true
        }

        if (userList.map { it.name }.contains(name)) {
            Toast.makeText(requireContext(), "Ігрок вже існує", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun renderingResponse(state: StateResponse) {
        when(state) {
            is StateResponse.SuccessResponse -> showUserList(state.userList)
            is StateResponse.ErrorResponse -> showError()
            StateResponse.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun showUserList(userList: List<UserModel>) {
        this.userList = userList
        binding.loading.visibility = View.GONE
        setAdapter(userList)
    }

    private fun showError() {
        binding.loading.visibility = View.GONE
        TODO()
    }

    private fun setAdapter(userList:  List<UserModel>) {
        adapter = RegistrationAdapter(userList)
        binding.recyclerAllUsers.adapter = adapter
    }

    private fun returnToGameFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }

    companion object {
        fun newInstance() = RegistrationFragment()
    }
}