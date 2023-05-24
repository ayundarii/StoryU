package com.dicoding.storyu.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.data.network.response.ApiResponse
import com.dicoding.storyu.databinding.FragmentHomeBinding
import com.dicoding.storyu.presentation.home.adapter.ListStoriesAdapter
import org.koin.android.ext.android.inject
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    private val listStoriesAdapter: ListStoriesAdapter by lazy {
        ListStoriesAdapter {
            navigateToDetail(it)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            rvStory.apply {
                adapter = listStoriesAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun initProcess() {
        viewModel.getStories()
    }

    override fun initObservers() {
        viewModel.storiesResult.observe(viewLifecycleOwner){ response ->
            Timber.d("Response is $response")
            when (response) {

                is ApiResponse.Success -> {
                    listStoriesAdapter.setData(response.data)
                }

                is ApiResponse.Error -> {
                    binding.root.showSnackBar("Can't get stories.")
                }

                is ApiResponse.Loading -> {

                }

                is ApiResponse.Empty -> {
                    binding.root.showSnackBar("There is no stories.")
                }
            }
        }

    }

    private fun navigateToDetail(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                id
            )
        )
    }

}