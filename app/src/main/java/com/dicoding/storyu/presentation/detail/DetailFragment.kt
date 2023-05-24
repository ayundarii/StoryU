package com.dicoding.storyu.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentDetailBinding


class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

    }

    override fun initProcess() {

    }

    override fun initObservers() {

    }

}