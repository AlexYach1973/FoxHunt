package com.alexyach.kotlin.foxhunt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.databinding.FragmentGameBinding
import com.alexyach.kotlin.foxhunt.model.ModelItemField
import com.alexyach.kotlin.foxhunt.model.User
import com.alexyach.kotlin.foxhunt.ui.adapter.GameAdapter
import com.alexyach.kotlin.foxhunt.ui.adapter.IClickItemAdapter
import com.alexyach.kotlin.foxhunt.ui.adapter.ILongClickItemAdapter
import com.alexyach.kotlin.foxhunt.utils.UserDataStore

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding!!

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this)[GameViewModel::class.java]
    }

    private lateinit var adapter: GameAdapter
    private lateinit var fieldList: List<ModelItemField>
    private var isWin = false

    private var userPreferences = User(
        numberOfGame = 0,
        minNumberOfMoves = 0,
        maxNumberOfMoves = 0,
        sumNumberOfMoves = 0,
        meanNumberOfMoves = 0.0
    )
    private lateinit var userDataStore: UserDataStore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // DataStore
        userDataStore = UserDataStore(requireContext())
        /** Спостереження DataStore*/
        dataStoreObserver()

        /** Спостереження */
        viewModel.getFieldList().observe(viewLifecycleOwner) { fieldList ->
            this.fieldList = fieldList
            setAdapter(fieldList)
        }

        viewModel.getIsWin().observe(viewLifecycleOwner) { isWin ->
            this.isWin = isWin
            showWin(isWin)
        }

        viewModel.getCountStep().observe(viewLifecycleOwner) { step ->
            binding.countStep.text = step.toString()
            setColorCountStep(step)
        }
        /** ------------- */

        // Button Restart
        binding.btnStart.setOnClickListener {
            viewModel.restartGame()
        }

    }

    private fun setColorCountStep(step: Int) {
        if (step <= userPreferences.minNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.green_number, null))

        } else if (step >= userPreferences.maxNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.red_number, null))

        } /*else if (step < userPreferences.maxNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.grey_number, null))

        } */else binding.countStep.setTextColor(resources.getColor(R.color.yellow_number, null))
    }

    private fun showWin(isWin: Boolean) {

        if (isWin) {
            saveDataStore()

            binding.gameField.alpha = 0.5F
            binding.gameField.scaleX = 0.75F
            binding.gameField.scaleY = 0.75F

            binding.btnStart.visibility = View.VISIBLE
            binding.llStatisticLong.visibility = View.VISIBLE
            binding.llStatisticShort.visibility = View.GONE

            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()

        } else {
            binding.gameField.alpha = 1F
            binding.gameField.scaleX = 1F
            binding.gameField.scaleY = 1F

            binding.btnStart.visibility = View.GONE
            binding.llStatisticLong.visibility = View.GONE
            binding.llStatisticShort.visibility = View.VISIBLE
        }
    }

    /** Adapter */
    private fun setAdapter(fieldList: List<ModelItemField>) {
        binding.gameField.layoutManager = GridLayoutManager(requireContext(), 9)
        adapter = GameAdapter(fieldList, listenerOneClick, listenerLongClick)
        binding.gameField.adapter = adapter
    }

    // Click
    private var listenerOneClick = IClickItemAdapter { position ->
        if (isWin) {
            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()
            return@IClickItemAdapter
        }
        viewModel.checkMarkerNotFox(position)
        adapter.notifyItemChanged(position)
    }

    // Long Click
    private val listenerLongClick = ILongClickItemAdapter { position ->
        if (isWin) {
            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()
            return@ILongClickItemAdapter
        }
        viewModel.action(position)
        adapter.notifyItemChanged(position)
    }

    /** ------------- */

    private fun dataStoreObserver() {
        userDataStore.numberOfGameGameFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.numberOfGame = it
            val textShort = "${resources.getText(R.string.number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.number_of_game_long)} $it"

            binding.tvNumberOfGameShort.text = textShort
            binding.tvNumberOfGameLong.text = textLong
        }
        userDataStore.minNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.minNumberOfMoves = it
            val textShort = "${resources.getText(R.string.min_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.min_number_of_game_long)} $it"

            binding.tvMinNumberOfMovesShort.text = textShort
            binding.tvMinNumberOfMovesLong.text = textLong
        }
        userDataStore.maxNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.maxNumberOfMoves = it
            val textShort = "${resources.getText(R.string.max_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.max_number_of_game_long)} $it"

            binding.tvMaxNumberOfMovesShort.text = textShort
            binding.tvMaxNumberOfMovesLong.text = textLong
        }
        userDataStore.sumNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.sumNumberOfMoves = it
        }
        userDataStore.meanNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.meanNumberOfMoves = it
            val textShort = "${resources.getText(R.string.mean_number_of_game_short)} ${String.format("%.1f", it)}"
            val textLong = "${resources.getText(R.string.mean_number_of_game_long)} ${String.format("%.1f", it)}"

            binding.tvMeanNumberOfMovesShort.text = textShort
            binding.tvMeanNumberOfMovesLong.text = textLong
        }
    }


    private fun saveDataStore() {
        viewModel.saveDataStore(userDataStore, userPreferences)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = GameFragment()
    }


}