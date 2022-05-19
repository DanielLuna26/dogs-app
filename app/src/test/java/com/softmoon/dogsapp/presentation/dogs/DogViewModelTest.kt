package com.softmoon.dogsapp.presentation.dogs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.softmoon.dogsapp.TestCoroutineRule
import com.softmoon.dogsapp.domain.interators.GetDogsUseCase
import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DogViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var getDogsUseCase: GetDogsUseCase

    lateinit var viewModel: DogViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = DogViewModel(getDogsUseCase)
    }

    @Test
    fun `Get dogs from use case successfully`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        whenever(getDogsUseCase()).thenReturn(
            flow {
                emit(
                    Resource.Success(
                        listOf(
                            Dog(
                                1,
                                "Rex",
                                "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
                                5,
                                "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
                            ),
                            Dog(
                                2,
                                "Spots",
                                "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
                                3,
                                "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
                            )
                        )
                    )
                )
            }
        )

        viewModel.getDogs()


        val viewStates = viewModel.viewState.take(2).toList()

        assertTrue(viewStates.first() is DogViewModel.ViewState.Loading)
        assertTrue(viewStates[1] is DogViewModel.ViewState.Success)
        assertTrue((viewStates[1] as DogViewModel.ViewState.Success).dogs.isNotEmpty())
        assertTrue((viewStates[1] as DogViewModel.ViewState.Success).dogs[0].name == "Rex")
    }

    @Test
    fun `Get dogs from use case successfully with empty list`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        whenever(getDogsUseCase()).thenReturn(
            flow {
                emit(
                    Resource.Success(
                        emptyList<Dog>()
                    )
                )
            }
        )

        viewModel.getDogs()


        val viewStates = viewModel.viewState.take(2).toList()

        assertTrue(viewStates.first() is DogViewModel.ViewState.Loading)
        assertTrue(viewStates[1] is DogViewModel.ViewState.Success)
        assertTrue((viewStates[1] as DogViewModel.ViewState.Success).dogs.isEmpty())
    }

    @Test
    fun `Get dogs from use case with error on server`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        whenever(getDogsUseCase()).thenReturn(
            flow {
                emit(
                    Resource.Failure(
                        false,
                        HttpURLConnection.HTTP_INTERNAL_ERROR,
                        null
                    )
                )
            }
        )

        viewModel.getDogs()


        val viewStates = viewModel.viewState.take(2).toList()

        assertTrue(viewStates.first() is DogViewModel.ViewState.Loading)
        assertTrue(viewStates[1] is DogViewModel.ViewState.Failure)
        assertFalse(viewStates[1] is DogViewModel.ViewState.Success)
        assertTrue(
            (viewStates[1] as DogViewModel.ViewState.Failure).res.errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR
        )
        assertFalse((viewStates[1] as DogViewModel.ViewState.Failure).res.isNetworkError)
    }

    @Test
    fun `Get dogs from use case without data`() = testCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        whenever(getDogsUseCase()).thenReturn(
            flow {
                emit(
                    Resource.Failure(
                        true,
                        null,
                        null
                    )
                )
            }
        )

        viewModel.getDogs()


        val viewStates = viewModel.viewState.take(2).toList()

        assertTrue(viewStates.first() is DogViewModel.ViewState.Loading)
        assertTrue(viewStates[1] is DogViewModel.ViewState.Failure)
        assertFalse(viewStates[1] is DogViewModel.ViewState.Success)
        assertTrue((viewStates[1] as DogViewModel.ViewState.Failure).res.isNetworkError)
    }
}