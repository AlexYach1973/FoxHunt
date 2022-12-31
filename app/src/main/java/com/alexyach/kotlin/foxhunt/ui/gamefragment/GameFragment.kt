package com.alexyach.kotlin.foxhunt.ui.gamefragment

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
import com.alexyach.kotlin.foxhunt.data.model.ModelItemField
import com.alexyach.kotlin.foxhunt.data.model.User
import com.alexyach.kotlin.foxhunt.ui.app.AppFoxHunt.Companion.getUserDataStore
import com.alexyach.kotlin.foxhunt.ui.gamefragment.adapter.GameAdapter
import com.alexyach.kotlin.foxhunt.ui.gamefragment.adapter.IClickItemAdapter
import com.alexyach.kotlin.foxhunt.ui.gamefragment.adapter.ILongClickItemAdapter
import com.alexyach.kotlin.foxhunt.utils.NAME_UNKNOWN

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
        name = "",
        numberOfGame = 0,
        minNumberOfMoves = 0,
        maxNumberOfMoves = 0,
        sumNumberOfMoves = 0,
        meanNumberOfMoves = 0.0
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        // Button Enter name
        binding.btnEnterName.setOnClickListener {
            val name = binding.etEnterName.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Пусте поле", Toast.LENGTH_SHORT).show()
            } else {
                userPreferences.name = name
                viewModel.saveUserName(getUserDataStore(), userPreferences)
                binding.enterNameLayout.visibility = View.GONE
            }
        }

    }

    private fun dataStoreObserver() {

        getUserDataStore().userName.asLiveData().observe(viewLifecycleOwner) {
//            val userName = "${resources.getText(R.string.user_name)} $it"
            binding.tvUserName.text = it

            if (it.equals(NAME_UNKNOWN)) {
                showEnterNameLayout()
                return@observe
            }
            userPreferences.name = it
        }

        getUserDataStore().numberOfGameGameFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.numberOfGame = it
            val textShort = "${resources.getText(R.string.number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.number_of_game_long)} $it"

            binding.tvNumberOfGameShort.text = textShort
            binding.tvNumberOfGameLong.text = textLong
        }

        getUserDataStore().minNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.minNumberOfMoves = it
            val textShort = "${resources.getText(R.string.min_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.min_number_of_game_long)} $it"

            binding.tvMinNumberOfMovesShort.text = textShort
            binding.tvMinNumberOfMovesLong.text = textLong
        }

        getUserDataStore().maxNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.maxNumberOfMoves = it
            val textShort = "${resources.getText(R.string.max_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.max_number_of_game_long)} $it"

            binding.tvMaxNumberOfMovesShort.text = textShort
            binding.tvMaxNumberOfMovesLong.text = textLong
        }

        getUserDataStore().sumNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.sumNumberOfMoves = it
        }

        getUserDataStore().meanNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userPreferences.meanNumberOfMoves = it
            val textShort = "${resources.getText(R.string.mean_number_of_game_short)} ${String.format("%.1f", it)}"
            val textLong = "${resources.getText(R.string.mean_number_of_game_long)} ${String.format("%.1f", it)}"

            binding.tvMeanNumberOfMovesShort.text = textShort
            binding.tvMeanNumberOfMovesLong.text = textLong
        }
    }

    private fun showEnterNameLayout() {
        binding.enterNameLayout.visibility = View.VISIBLE
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

    // Click for Adapter
    private var listenerOneClick = IClickItemAdapter { position ->
        if (isWin) {
            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()
            return@IClickItemAdapter
        }
        viewModel.checkMarkerNotFox(position)
        adapter.notifyItemChanged(position)
    }

    // Long Click for Adapter
    private val listenerLongClick = ILongClickItemAdapter { position ->
        if (isWin) {
            Toast.makeText(requireActivity(), "Гра закінчена !!!", Toast.LENGTH_SHORT).show()
            return@ILongClickItemAdapter
        }
        viewModel.action(position)
        adapter.notifyItemChanged(position)
    }

    /** ------------- */

    private fun saveDataStore() {
        viewModel.saveDataStore(getUserDataStore(), userPreferences)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = GameFragment()
    }


}