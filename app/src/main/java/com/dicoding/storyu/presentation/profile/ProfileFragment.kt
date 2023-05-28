package com.dicoding.storyu.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentProfileBinding
import com.dicoding.storyu.utils.PreferenceManager
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val preference: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            tvUsername.text = preference.getUsername()

            buttonLogout.setOnClickListener {
                preference.clearAllPreferences()
                navigateToLogin()
            }
        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        )
    }
}