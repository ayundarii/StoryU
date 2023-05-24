package com.dicoding.storyu.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentRegisterBinding
import org.koin.android.ext.android.inject


class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        TODO("Not yet implemented")
    }

    override fun initUI() {
        TODO("Not yet implemented")
    }

    override fun initProcess() {
        TODO("Not yet implemented")
    }

    override fun initObservers() {
        TODO("Not yet implemented")
    }

}