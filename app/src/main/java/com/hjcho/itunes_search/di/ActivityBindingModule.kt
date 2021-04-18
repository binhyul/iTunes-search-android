package com.hjcho.itunes_search.di

import com.hjcho.itunes_search.ui.MainActivity
import com.hjcho.itunes_search.ui.MainModule
//import com.hjcho.itunes_search.ui.MainModule
//import com.hjcho.itunes_search.ui.MainTabDelegateModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            MainModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity


}
