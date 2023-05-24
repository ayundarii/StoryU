package com.dicoding.storyu.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.R
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentLoginBinding

import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            loginButton.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()
                viewModel.login(
                    email,
                    password )
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner){ response ->
            Timber.d("Response is $response")
            when (response) {

                is ApiResponse.Success -> {
                    binding.root.showSnackBar("Login Success")
                    navigateToHome()
                }

                is ApiResponse.Error -> {
                    binding.root.showSnackBar("Check your email and password")
                }

                is ApiResponse.Loading -> {

                }

                is ApiResponse.Empty -> {

                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment
        )
    }

}