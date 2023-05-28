package com.dicoding.storyu.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyu.base.BaseFragment
import com.dicoding.storyu.databinding.FragmentHomeBinding
import com.dicoding.storyu.presentation.home.adapter.LoadingStateAdapter
import com.dicoding.storyu.presentation.home.adapter.StoryPagingAdapter
import org.koin.android.ext.android.inject


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by inject()

    private val storyPagingAdapter: StoryPagingAdapter by lazy {
        StoryPagingAdapter {
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
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        binding.apply {
            fabAddStory.setOnClickListener {
                navigateToAddStory()
            }
            rvStory.apply {
                adapter = storyPagingAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        storyPagingAdapter.retry()
                    }
                )
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.getStory().observe(this) {
            storyPagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun navigateToDetail(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                id
            )
        )
    }

    private fun navigateToAddStory() {
       findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToAddStoryFragment()
        )
    }
}