package com.alexyach.kotlin.foxhunt.presentation.ui.app

import android.app.Application
import android.util.Log
import com.alexyach.kotlin.foxhunt.di.modules
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin
        startKoin {
//            androidLogger(Level.DEBUG)
            androidContext(this@App)
            // setup a KoinFragmentFactory instance
            fragmentFactory()
            modules(modules)
        }

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


}