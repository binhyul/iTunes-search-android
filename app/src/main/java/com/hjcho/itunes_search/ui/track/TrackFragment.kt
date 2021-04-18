package com.hjcho.itunes_search.ui.track

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hjcho.itunes_search.databinding.FragmentTrackBinding
import com.hjcho.itunes_search.ui.MainViewModelHolder
import com.hjcho.itunes_search.ui.common.BaseFragment
import timber.log.Timber

class TrackFragment : BaseFragment() {

    override val TAG: String = "TrackFragment"

    lateinit var binding: FragmentTrackBinding

    private lateinit var mainViewModelHolder: MainViewModelHolder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTrackBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = mainViewModelHolder.getMainViewModel()
        }


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mainViewModelHolder = context as MainViewModelHolder
        } catch (e: Exception) {
            Timber.e("Error on casting")
            e.printStackTrace()
        }
    }
}