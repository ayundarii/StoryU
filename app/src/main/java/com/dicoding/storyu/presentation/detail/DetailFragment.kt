package com.dicoding.storyu.presentation.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject
import timber.log.Timber


class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by inject()

    private var id = ""

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            toolBar.setNavigationOnClickListener{
                navigateToHome()
            }
        }
    }

    override fun initIntent() {
        val safeArgs = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        id = safeArgs?.id ?: ""
    }

    override fun initProcess() {
        viewModel.getStoryDetail(id)
    }

    override fun initObservers() {
        viewModel.detailResult.observe(viewLifecycleOwner) { response ->
            Timber.d("Response is $response")
            when (response) {
                is ApiResponse.Success -> {
                    binding.apply {
                        tvNameDetail.text = response.data.name
                        tvCreatedAt.text = displayTime(response.data.createdAt)
                        tvDescriptionDetail.text = response.data.description
                        Glide.with(binding.root.context)
                            .load(response.data.photoUrl)
                            .into(binding.imgStory)
                    }
                }

                is ApiResponse.Error -> {
                    binding.root.showSnackBar(response.errorMessage)
                }

                is ApiResponse.Loading -> {

                }

                is ApiResponse.Empty -> {}
            }
        }
    }

    private fun navigateToHome(){
        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentToHomeFragment()
        )
    }

    private fun displayTime(createdAt: String): String{
        val date = createdAt.substring(0, 10)
        val time = createdAt.substring(12, 19)

        return "$time | $date"
    }

}