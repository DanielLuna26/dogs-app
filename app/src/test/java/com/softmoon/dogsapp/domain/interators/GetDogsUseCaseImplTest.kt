package com.softmoon.dogsapp.domain.interators

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.softmoon.dogsapp.CoroutineTestRule
import com.softmoon.dogsapp.data.local.entity.DogEntity
import com.softmoon.dogsapp.domain.model.Dog
import com.softmoon.dogsapp.domain.repository.DogsRepository
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
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
class GetDogsUseCaseImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Mock
    lateinit var repository: DogsRepository

    lateinit var getDogsUseCase: GetDogsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        getDogsUseCase = GetDogsUseCaseImpl(repository)
    }

    @Test
    fun `Get dogs from repository successfully`() = runBlocking {
        whenever(
            repository.getDogs()
        ).thenReturn(
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

        val data = getDogsUseCase().toList()

        assertTrue(data.isNotEmpty())
        assertTrue(data.first() is Resource.Success)
        assertTrue((data.first() as Resource.Success).value.isNotEmpty())
    }

    @Test
    fun `Get dogs from repository successfully with empty list`() = runBlocking {
        whenever(
            repository.getDogs()
        ).thenReturn(
            flow {
                emit(
                    Resource.Success(
                        emptyList<Dog>()
                    )
                )
            }
        )

        val data = getDogsUseCase().toList()

        assertTrue(data.isNotEmpty())
        assertTrue(data.first() is Resource.Success)
        assertTrue((data.first() as Resource.Success).value.isEmpty())
    }

    @Test
    fun `Get dogs first time from repository without data`() = runBlocking {
        whenever(
            repository.getDogs()
        ).thenReturn(
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

        val data = getDogsUseCase().toList()

        assertTrue(data.isNotEmpty())
        assertTrue(data.first() is Resource.Failure)
        assertTrue((data.first() as Resource.Failure).isNetworkError)
    }

    @Test
    fun `Get dogs first time from repository with internal error from server`() = runBlocking {
        whenever(
            repository.getDogs()
        ).thenReturn(
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

        val data = getDogsUseCase().toList()

        assertTrue(data.isNotEmpty())
        assertTrue(data.first() is Resource.Failure)
        assertTrue(!(data.first() as Resource.Failure).isNetworkError)
        assertTrue((data.first() as Resource.Failure).errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

}