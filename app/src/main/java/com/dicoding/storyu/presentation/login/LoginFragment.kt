package com.dicoding.storyu.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.R
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.data.network.response.LoginResponse
import com.dicoding.storyu.databinding.FragmentLoginBinding
import com.dicoding.storyu.presentation.detail.DetailFragmentArgs
import com.dicoding.storyu.utils.observeOnce

import org.koin.android.ext.android.inject
import timber.log.Timber

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by inject()

    private var isRegistrationSuccessful = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        Timber.d("LoginFragment onViewCreated")
        if(isRegistrationSuccessful) binding.root.showSnackBar("Account successfully created, go and login.")

        binding.apply {
            loginButton.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()
                viewModel.login(email, password)
            }

            tvRegister.setOnClickListener {
                navigateToRegister()
            }
        }
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { LoginFragmentArgs.fromBundle(it) }
        isRegistrationSuccessful = safeArgs?.isRegistrationSuccessful ?: false
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        viewModel.loginResult.observe(viewLifecycleOwner) { response: ApiResponse<String> ->
            Timber.d("Response is $response")
            when (response) {

                is ApiResponse.Success -> {
                    Timber.d("success")
                    hideLoadingDialog()
                    isRegistrationSuccessful = false
                    navigateToHome()
                }

                is ApiResponse.Error -> {
                    hideLoadingDialog()
                    binding.root.showSnackBar("Check your email and password")
                }

                is ApiResponse.Loading -> {
                    showLoadingDialog()
                }

                is ApiResponse.Empty -> {

                }
            }
        }
    }

    private fun navigateToHome() {
        Timber.d("going to home")
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    private fun navigateToRegister() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        )
    }

}