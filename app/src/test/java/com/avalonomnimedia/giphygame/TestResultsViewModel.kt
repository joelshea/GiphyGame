package com.avalonomnimedia.giphygame

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.avalonomnimedia.giphygame.service.DictionaryResponse
import com.avalonomnimedia.giphygame.service.IDictionaryService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor


/**
 * "uut" stands for "Unit Under Test"
 */
@ExperimentalCoroutinesApi
class TestResultsViewModel {
    @get:Rule
    public var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun whenSearchTermAndGuessMatchReturnPerfectScoreText() = runBlockingTest {
        val searchTerm = "search term"
        val guess = searchTerm
        val expected = ResultsData(R.string.results_perfect_score)

        val dictionaryService = mockk<IDictionaryService>()

        val uut = ResultsViewModel(dictionaryService)
        val liveData = uut.calculateScore(searchTerm, guess)

        liveData.observeForever {  }

        val actual = liveData.value

        assertEquals(expected, actual)
    }

    @Test
    fun whenSearchTermAndGuessDoNotMatchButGuessInListReturnScoreText() = runBlockingTest {
        val searchTerm = "search term"
        val guess = "something else"
        val score = 100
        val expected = ResultsData(R.string.results_some_points, score)

        val dictionaryService = mockk<IDictionaryService>()

        coEvery { dictionaryService.getSynonyms(searchTerm) } returns listOf(DictionaryResponse(guess, score, listOf()))

        val uut = ResultsViewModel(dictionaryService)
        val liveData = uut.calculateScore(searchTerm, guess)

        liveData.observeForever {  }

        val actual = liveData.value

        assertEquals(expected, actual)
    }

    @Test
    fun whenSearchTermAndGuessDoNotMatchAndGuessNotInListReturn0PointsText() = runBlockingTest {
        val searchTerm = "search term"
        val guess = "something else"
        val listWord = "Not that"
        val expected = ResultsData(R.string.results_no_points)

        val dictionaryService = mockk<IDictionaryService>()

        coEvery { dictionaryService.getSynonyms(searchTerm) } returns listOf(DictionaryResponse(listWord, 123, listOf()))

        val uut = ResultsViewModel(dictionaryService)
        val liveData = uut.calculateScore(searchTerm, guess)

        liveData.observeForever {  }

        val actual = liveData.value

        assertEquals(expected, actual)
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}