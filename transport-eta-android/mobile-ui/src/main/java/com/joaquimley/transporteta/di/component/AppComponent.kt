package com.joaquimley.transporteta.di.component

import android.app.Application
import com.joaquimley.transporteta.App
import com.joaquimley.transporteta.di.module.ActivityBindingModule
import com.joaquimley.transporteta.di.module.AppModule
import com.joaquimley.transporteta.di.module.SmsControllerModule
import com.joaquimley.transporteta.ui.injection.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = arrayOf(
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        SmsControllerModule::class
))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
