package com.joaquimley.transporteta

import android.app.Activity
import android.app.Application
import android.content.IntentFilter
import android.provider.Telephony
import com.joaquimley.transporteta.di.component.DaggerAppComponent
import com.joaquimley.transporteta.sms.SmsBroadcastReceiver
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by joaquimley on 25/03/2018.
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var smsBroadcastReceiver: SmsBroadcastReceiver

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
        registerReceiver(smsBroadcastReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}