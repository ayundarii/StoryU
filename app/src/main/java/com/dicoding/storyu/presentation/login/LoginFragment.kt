package com.dicoding.storyu.presentation.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentLoginBinding
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
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
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

        playAnimation()
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { LoginFragmentArgs.fromBundle(it) }
        isRegistrationSuccessful = safeArgs?.isRegistrationSuccessful ?: false
    }

    override fun initProcess() {
    }

    override fun initObservers() {}

    private fun navigateToRegister() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        )
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val logo = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvTitle = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val tvMessage = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val tvRegisterMessage = ObjectAnimator.ofFloat(binding.tvRegisterMessage, View.ALPHA, 1f).setDuration(500)
        val tvRegister = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(tvRegisterMessage, tvRegister)
        }

        AnimatorSet().apply {
            playSequentially(logo, tvTitle, tvMessage, tvEmail, emailEditTextLayout, tvPassword, passwordEditTextLayout, login, together)
            startDelay = 500
        }.start()
    }


}