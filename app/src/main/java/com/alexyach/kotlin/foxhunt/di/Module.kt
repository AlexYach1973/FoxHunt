package com.alexyach.kotlin.foxhunt.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.alexyach.kotlin.foxhunt.data.datastore.UserDataStore
import com.alexyach.kotlin.foxhunt.data.repository.AWSStorageCoroutinesImpl
import com.alexyach.kotlin.foxhunt.domain.FoxHuntGame
import com.alexyach.kotlin.foxhunt.domain.repository.IAWSStorage
import com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.GameFragment
import com.alexyach.kotlin.foxhunt.presentation.ui.gamefragment.GameViewModel
import com.alexyach.kotlin.foxhunt.presentation.ui.listplayers.ListPlayersFragment
import com.alexyach.kotlin.foxhunt.presentation.ui.listplayers.ListPlayersViewModel
import com.alexyach.kotlin.foxhunt.presentation.ui.registration.RegistrationFragment
import com.alexyach.kotlin.foxhunt.presentation.ui.registration.RegistrationViewModel
import com.alexyach.kotlin.foxhunt.utils.USER_PREFERENCES_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {androidContext().preferencesDataStoreFile(USER_PREFERENCES_NAME)}
        )
    }

    factory <UserDataStore> {
        get()
    }

    factory<FoxHuntGame> {
        FoxHuntGame()
    }

    factory<IAWSStorage> {
        AWSStorageCoroutinesImpl()
    }

    fragment<GameFragment> {
        GameFragment(UserDataStore(get()))
    }
    fragment<RegistrationFragment> {
        RegistrationFragment()
    }
    fragment<ListPlayersFragment> {
        ListPlayersFragment()
    }

    viewModel<GameViewModel> {
        GameViewModel(
            FoxHuntGame(),
            AWSStorageCoroutinesImpl(),
            UserDataStore(get())
        )
    }

    viewModel<ListPlayersViewModel> {
        ListPlayersViewModel(
            AWSStorageCoroutinesImpl()
        )
    }

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(
            AWSStorageCoroutinesImpl(),
            UserDataStore(get())
        )
    }

}