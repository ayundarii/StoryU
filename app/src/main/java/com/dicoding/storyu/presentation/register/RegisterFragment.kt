package com.dicoding.storyu.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.R
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentRegisterBinding
import org.koin.android.ext.android.inject
import timber.log.Timber


class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
       return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            registerButton.setOnClickListener {
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()
                viewModel.register(name, email, password)
            }

            tvLogin.setOnClickListener {
                navigateToLogin()
            }
        }

    }

    override fun initProcess() {

    }

    override fun initObservers() {
        viewModel.registerResult.observe(viewLifecycleOwner){ response ->
            Timber.d("Response is $response")
            when (response) {

                is ApiResponse.Success -> {
                    binding.root.showSnackBar("Account successfully created")
                }

                is ApiResponse.Error -> {
                    binding.root.showSnackBar("Check your name, email and password")
                }

                is ApiResponse.Loading -> {

                }

                is ApiResponse.Empty -> {

                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_registerFragment_to_loginFragment
        )
    }

}