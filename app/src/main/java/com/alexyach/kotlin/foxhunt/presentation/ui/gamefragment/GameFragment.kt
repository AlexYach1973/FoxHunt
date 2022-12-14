package com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.alexyach.kotlin.foxhunt.R
import com.alexyach.kotlin.foxhunt.data.datastore.UserDataStore
import com.alexyach.kotlin.foxhunt.data.model.UserModel
import com.alexyach.kotlin.foxhunt.databinding.FragmentGameBinding
import com.alexyach.kotlin.foxhunt.domain.model.ModelItemField
import com.alexyach.kotlin.foxhunt.presentation.ui.base.BaseFragment
import com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.adapter.GameAdapter
import com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.adapter.IClickItemAdapter
import com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.adapter.ILongClickItemAdapter
import com.alexyach.kotlin.foxhunt.presentation.ui.listplayers.ListPlayersFragment
import com.alexyach.kotlin.foxhunt.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GameFragment(
    private val dataStore: UserDataStore
) : BaseFragment<FragmentGameBinding, GameViewModel>() {

    // Створюємо через Koin
    override val viewModel by viewModel<GameViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGameBinding.inflate(inflater, container, false)

    private lateinit var adapter: GameAdapter
    private lateinit var fieldList: List<ModelItemField>
    private var isWin = false
    private var gameEnd = false

    private var userModelPreferences = UserModel(
        name = "",
        numberOfGame = 0,
        minNumberOfMoves = 0,
        maxNumberOfMoves = 0,
        sumNumberOfMoves = 0,
        meanNumberOfMoves = 0.0
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Спостереження DataStore*/
        dataStoreObserver()

        /** Спостереження */
        viewModel.getFieldList().observe(viewLifecycleOwner) { fieldList ->
            this.fieldList = fieldList
            setAdapter(fieldList)
        }

        viewModel.getGameEnd().observe(viewLifecycleOwner) { gameEnd ->
            this.gameEnd = gameEnd
            saveDataStore(gameEnd)
        }

        viewModel.getIsWin().observe(viewLifecycleOwner) { isWin ->
            this.isWin = isWin
            showWin(isWin)
        }

        viewModel.getCountStep().observe(viewLifecycleOwner) { step ->
            binding.countStep.text = step.toString()
            setColorCountStep(step)
        }

        // Button Restart
        binding.btnStart.setOnClickListener {
            gameEnd = false
            viewModel.restartGame()
        }

        // Button List Players
        binding.btnListPlayers.setOnClickListener {
            goToListPlayersFragment()
        }
    }

    private fun dataStoreObserver() {
        dataStore.userName.asLiveData().observe(viewLifecycleOwner) {
            binding.tvUserName.text = it

            if (it.equals(NAME_UNKNOWN)) {
                goToRegistrationFragment()
                return@observe
            }
            userModelPreferences.name = it
            showThisFragment()
        }

        dataStore.numberOfGameGameFlow.asLiveData().observe(viewLifecycleOwner) {
            userModelPreferences.numberOfGame = it
            val textShort = "${resources.getText(R.string.number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.number_of_game_long)} $it"

            binding.tvNumberOfGameShort.text = textShort
            binding.tvNumberOfGameLong.text = textLong
        }

        dataStore.minNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userModelPreferences.minNumberOfMoves = it
            val textShort = "${resources.getText(R.string.min_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.min_number_of_game_long)} $it"

            binding.tvMinNumberOfMovesShort.text = textShort
            binding.tvMinNumberOfMovesLong.text = textLong
        }

        dataStore.maxNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userModelPreferences.maxNumberOfMoves = it
            val textShort = "${resources.getText(R.string.max_number_of_game_short)} $it"
            val textLong = "${resources.getText(R.string.max_number_of_game_long)} $it"

            binding.tvMaxNumberOfMovesShort.text = textShort
            binding.tvMaxNumberOfMovesLong.text = textLong
        }

        dataStore.sumNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userModelPreferences.sumNumberOfMoves = it
        }

        dataStore.meanNumberOfMovesFlow.asLiveData().observe(viewLifecycleOwner) {
            userModelPreferences.meanNumberOfMoves = it
            val textShort = "${resources.getText(R.string.mean_number_of_game_short)} ${
                String.format("%.1f", it)}"
            val textLong = "${resources.getText(R.string.mean_number_of_game_long)} ${
                String.format("%.1f", it)}"

            binding.tvMeanNumberOfMovesShort.text = textShort
            binding.tvMeanNumberOfMovesLong.text = textLong
        }
    }

    private fun setColorCountStep(step: Int) {
        if (step <= userModelPreferences.minNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.green_number, null))

        } else if (step >= userModelPreferences.maxNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.red_number, null))

        } /*else if (step < userModelPreferences.maxNumberOfMoves) {
            binding.countStep.setTextColor(resources.getColor(R.color.grey_number, null))

        } */ else binding.countStep.setTextColor(resources.getColor(R.color.yellow_number, null))
    }

    private fun showWin(isWin: Boolean) {
        if (isWin) {
            binding.gameField.alpha = 0.5F
            binding.gameField.scaleX = 0.75F
            binding.gameField.scaleY = 0.75F

            binding.btnStart.visibility = View.VISIBLE
            binding.llStatisticLong.visibility = View.VISIBLE
            binding.llStatisticShort.visibility = View.GONE

            toast("Гра закінчена !!!")
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
            toast("Гра закінчена !!!")
            return@IClickItemAdapter
        }
        viewModel.checkMarkerNotFox(position)
        adapter.notifyItemChanged(position)
    }

    // Long Click for Adapter
    private val listenerLongClick = ILongClickItemAdapter { position ->
        if (isWin) {
            toast("Гра закінчена !!!")
            return@ILongClickItemAdapter
        }
        viewModel.action(position)
        adapter.notifyItemChanged(position)
    }
    /** ------------- */

    private fun saveDataStore(gameEnd: Boolean) {
        if (gameEnd) {
            viewModel.saveDataStore(userModelPreferences)
        }

    }

    private fun goToRegistrationFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .hide(this)
            .commit()
    }

    private fun goToListPlayersFragment() {
        arguments = Bundle().apply {
            putString(KEY_USER_NAME, userModelPreferences.name)
        }
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, ListPlayersFragment::class.java, arguments)
            .commit()
    }

    private fun showThisFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .show(this)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Game Fragment onDestroy()")
    }

}