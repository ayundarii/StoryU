package com.dicoding.storyu.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.storyu.R
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

    }

    override fun initProcess() {

    }

    override fun initObservers() {

    }

}