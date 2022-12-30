package com.alexyach.kotlin.foxhunt.ui.app

import android.app.Application
import com.alexyach.kotlin.foxhunt.data.datastore.UserDataStore

class AppFoxHunt: Application() {

    override fun onCreate() {
        super.onCreate()
        appFoxHunt = this
    }

    companion object {
        // Context
        private var appFoxHunt: AppFoxHunt? = null
        fun getAppFoxHunt() = appFoxHunt!!

        // DataStore singleton
        private var userDataStore: UserDataStore? = null
        fun getUserDataStore(): UserDataStore {
            if (userDataStore == null) {
                userDataStore = UserDataStore(getAppFoxHunt())
            }
            return userDataStore!!
        }
    }

}