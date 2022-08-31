package com.alexyach.kotlin.foxhunt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alexyach.kotlin.foxhunt.databinding.FragmentGameBinding
import com.alexyach.kotlin.foxhunt.model.ModelItemField

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding!!

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    private lateinit var adapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Спостереження */
        viewModel.getFieldList().observe(viewLifecycleOwner) { fieldList ->
            setAdapter(fieldList)
        }

        viewModel.getIsWin().observe(viewLifecycleOwner) { isWin ->
            showWin(isWin)
        }

        // Button Restart
        binding.btnStart.setOnClickListener {
            viewModel.restartGame()
        }


    }

    private fun showWin(isWin: Boolean) {
        if (isWin) {
            binding.gameField.alpha = 0.35F
            binding.gameField.isClickable = false
            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()

        } else {
            binding.gameField.alpha = 1F
            binding.gameField.isClickable = true
        }
    }

    private fun setAdapter(fieldList: List<ModelItemField>) {
        binding.gameField.layoutManager = GridLayoutManager(requireContext(), 9)

        adapter = GameAdapter(fieldList) { position ->

            if (viewModel.getIsWin().value == true) return@GameAdapter

            viewModel.action(position)

            Toast.makeText(requireContext(), "FOX: ${position}", Toast.LENGTH_SHORT).show()
        }
        binding.gameField.adapter = adapter
    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = GameFragment()
    }


}