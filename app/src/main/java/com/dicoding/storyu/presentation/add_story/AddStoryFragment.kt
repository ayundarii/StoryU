package com.dicoding.storyu.presentation.add_story

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.storyu.R
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentAddStoryBinding

class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding {
        return FragmentAddStoryBinding.inflate(inflater, container, false)
    }

    override fun initUI() {

    }

    override fun initProcess() {

    }

    override fun initObservers() {

    }

}