package com.dicoding.storyu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.data.repository.StoryRepository
import com.dicoding.storyu.paging_story.CoroutinesTestRule
import com.dicoding.storyu.paging_story.DummyData
import com.dicoding.storyu.presentation.home.HomeViewModel
import com.dicoding.storyu.presentation.home.adapter.StoryPagingAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        storyRepository = Mockito.mock(StoryRepository::class.java)
        homeViewModel = HomeViewModel(storyRepository)
    }

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    @Test
    fun `Stories Paging Should Not Null`() = runTest {
        val dataDummyStories = DummyData.listStoryDummy()
        val data = PagingDataSourceTest.snapshot(dataDummyStories)

        val dataStories = MutableLiveData<PagingData<Story>>()
        dataStories.value = data

        Mockito.`when`(storyRepository.getPagingStories()).thenReturn(dataStories)

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )

        val result = homeViewModel.getStory().getOrAwaitValue()

        differ.submitData(result)
        differ.itemCount < 1
        advanceUntilIdle()

        Assert.assertEquals(dataDummyStories.size, differ.snapshot().size)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dataDummyStories.size, differ.snapshot().size)
        Assert.assertEquals(dataDummyStories[0], differ.snapshot()[0])
    }


    @Test
    fun `Stories length should be the same`() = runTest {
        val dataDummyStories = DummyData.listStoryDummy()
        val data = PagingDataSourceTest.snapshot(dataDummyStories)

        val dataStories = MutableLiveData<PagingData<Story>>()
        dataStories.value = data

        Mockito.`when`(storyRepository.getPagingStories()).thenReturn(dataStories)

        val result = homeViewModel.getStory().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        differ.submitData(result)
        advanceUntilIdle()

        Assert.assertEquals(dataDummyStories.size, differ.snapshot().size)
    }

    @Test
    fun `First Story item should be the same`() = runTest {
        val dataDummyStories = DummyData.listStoryDummy()
        val data = PagingDataSourceTest.snapshot(dataDummyStories)

        val dataStories = MutableLiveData<PagingData<Story>>()
        dataStories.value = data

        Mockito.`when`(storyRepository.getPagingStories()).thenReturn(dataStories)

        val result = homeViewModel.getStory().getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        differ.submitData(result)
        advanceUntilIdle()

        Assert.assertEquals(dataDummyStories[0], differ.snapshot()[0])
    }

    @Test
    fun `If Story empty should return true`() = runTest {
        val dataDummyStories = DummyData.emptyListStoryDummy()
        val data = PagingDataSourceTest.snapshot(dataDummyStories)

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.DIFF_CALLBACK,
            updateCallback = listUpdateCallback,
            mainDispatcher = mainCoroutineRule.dispatcher,
            workerDispatcher = mainCoroutineRule.dispatcher
        )
        differ.submitData(data)
        advanceUntilIdle()

        Assert.assertEquals(differ.snapshot().isEmpty(), true)
    }

    private val listUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}