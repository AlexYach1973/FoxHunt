package com.alexyach.kotlin.foxhunt.presentation.ui.app

import android.app.Application
import android.util.Log
import com.alexyach.kotlin.foxhunt.data.datastore.UserDataStore
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin

class AppFoxHunt: Application() {

    override fun onCreate() {
        super.onCreate()
        appFoxHunt = this

        try {
            // DataStore's
            Amplify.addPlugin(AWSDataStorePlugin())
            // DataStore's cloud synchronization
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)

            Log.d("myLogs", "Wow !!! Initialized Amplify !!!")
        }catch (e: AmplifyException) {
            Log.d("myLogs", "Could not initialize Amplify", e)
        }

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