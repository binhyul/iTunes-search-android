package com.hjcho.itunes_search.ui

import android.os.Bundle
import android.widget.ToggleButton
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.hjcho.itunes_search.R
import com.hjcho.itunes_search.databinding.ActivityMainBinding
import com.hjcho.itunes_search.ui.common.BaseFragment
import com.hjcho.itunes_search.ui.favorite.FavoriteFragment
import com.hjcho.itunes_search.ui.track.TrackFragment
import com.hjcho.itunes_search.util.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject


interface MainViewModelHolder {
    fun getMainViewModel(): MainViewModel
}


class MainActivity : DaggerAppCompatActivity(), MainViewModelHolder {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivityMainBinding

    private var mainModel: MainViewModel? = null
    override fun getMainViewModel(): MainViewModel {
        if (mainModel == null) mainModel = viewModelProvider(viewModelFactory)
        return mainModel!!
    }

    private val menuItemIds = listOf(R.id.mu_track, R.id.mu_favorite)
    private val menuFragments = listOf(TrackFragment(), FavoriteFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            vm = getMainViewModel()
        }
        createMenuTabs()
        binding.bnMenu.setOnNavigationItemSelectedListener {
            selectMenuId(it.itemId)
        }


        setContentView(binding.root)
    }


    private fun selectMenuId(id: Int): Boolean {
        when (id) {
            menuItemIds[0] -> {
                replaceFragment(menuFragments[0])
            }
            menuItemIds[1] -> {
                replaceFragment(menuFragments[1])
            }
            else -> throw IllegalStateException("Selected tab is not exist")
        }
        return true
    }

    private fun createMenuTabs() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_page, menuFragments[0], menuFragments[0].TAG)
        transaction.add(R.id.fl_page, menuFragments[1], menuFragments[1].TAG)
        transaction.show(menuFragments[0])
        transaction.hide(menuFragments[1])
        transaction.commit()
    }

    private fun replaceFragment(showFragment: BaseFragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach { fragment ->
            val existFragment = supportFragmentManager.findFragmentByTag(showFragment.TAG)
            if (fragment == existFragment) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }

}

@BindingAdapter("android:songItem", "app:onCheckedFavoriteListener")
fun setOnCheckedFavoriteListener(
    view: ToggleButton,
    songItem: SongViewItem,
    actionListener: SongActionListener
) {
    view.setOnCheckedChangeListener { buttonView, isChecked ->
        actionListener.onCheckFavorite(songItem.songModel.trackId, isChecked)
    }
}
