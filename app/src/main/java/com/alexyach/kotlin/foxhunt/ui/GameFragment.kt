package com.alexyach.kotlin.foxhunt.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alexyach.kotlin.foxhunt.databinding.FragmentGameBinding
import com.alexyach.kotlin.foxhunt.model.ModelItemField

class GameFragment : Fragment(), IClickItemAdapter {

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

        /** Створюємо тестовий список*/

        setAdapter(createFieldItem())

    }

    private fun setAdapter(fieldList: List<ModelItemField>) {
        binding.gameField.layoutManager = GridLayoutManager(requireContext(),9)

        adapter = GameAdapter(fieldList, this)
        binding.gameField.adapter = adapter
    }

    override fun clickItemAdapter(field: ModelItemField) {
        Log.d("myLogs", "GameFragment listener: ${field.countFox}")
    }

    private fun createFieldItem(): List<ModelItemField>{
        val dataList: MutableList<ModelItemField> = mutableListOf()

        for (i in 1..81) {
            dataList.add(ModelItemField(countFox = 5, image = ""))
        }

        return dataList
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = GameFragment()
    }



}