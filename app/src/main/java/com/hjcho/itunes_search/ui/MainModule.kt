package com.hjcho.itunes_search.ui

import androidx.lifecycle.ViewModel
import com.hjcho.itunes_search.di.ViewModelKey
import com.hjcho.itunes_search.ui.favorite.FavoriteFragment
import com.hjcho.itunes_search.ui.track.TrackFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
@Suppress("UNUSED")
abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun contributeTrackFragment(): TrackFragment

    @ContributesAndroidInjector
    internal abstract fun contributeFavoriteFragment(): FavoriteFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}


