package com.hjcho.itunes_search

import android.widget.Toast
import com.hjcho.itunes_search.di.DaggerAppComponent
import com.hjcho.itunes_search.domain.IllegalResponseException
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

class MainApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        setupUncaughtExceptionHandler()
    }

    private fun showToast(text: String) {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            e.printStackTrace()
            when (e) {
                is UnknownHostException -> showToast("인터넷 연결을 확인해 주세요.")
                is IllegalResponseException -> {
                    showToast("일시적인 오류입니다.")
                }
                is IllegalArgumentException, is NullPointerException -> {
                    showToast("알 수 없는 오류가 발생했습니다")
                }
            }
        }
    }


}


