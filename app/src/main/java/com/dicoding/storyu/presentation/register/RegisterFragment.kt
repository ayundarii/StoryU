package com.dicoding.storyu.presentation.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
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
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        binding.apply {
            registerButton.setOnClickListener {
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()
                viewModel.register(name, email, password)
            }

            tvLogin.setOnClickListener {
                navigateToLogin(false)
            }
        }

        playAnimation()
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

    private fun navigateToLogin(isRegistrationSuccessful: Boolean) {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(
            isRegistrationSuccessful
        )
        findNavController().navigate(action)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val logo = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val tvTitle = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val tvMessage =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)
        val tvLoginMessage =
            ObjectAnimator.ofFloat(binding.tvLoginMessage, View.ALPHA, 1f).setDuration(500)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(tvLoginMessage, tvLogin)
        }

        AnimatorSet().apply {
            playSequentially(
                logo,
                tvTitle,
                tvMessage,
                tvName,
                nameEditTextLayout,
                tvEmail,
                emailEditTextLayout,
                tvPassword,
                passwordEditTextLayout,
                register,
                together
            )
            startDelay = 500
        }.start()
    }

}