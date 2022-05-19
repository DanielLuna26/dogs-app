package com.softmoon.dogsapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.softmoon.dogsapp.CoroutineTestRule
import com.softmoon.dogsapp.MockServerBaseTest
import com.softmoon.dogsapp.data.local.daos.DogsDao
import com.softmoon.dogsapp.data.remote.services.DogsApi
import com.softmoon.dogsapp.data.repository.mappers.DogsDomainMapper
import com.softmoon.dogsapp.data.repository.mappers.DogsEntityMapper
import com.softmoon.dogsapp.domain.repository.DogsRepository
import com.softmoon.dogsapp.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import com.nhaarman.mockitokotlin2.whenever
import com.softmoon.dogsapp.data.local.entity.DogEntity
import com.softmoon.dogsapp.domain.model.Dog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DogsRepositoryImplTest: MockServerBaseTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    override fun isMockServerEnabled(): Boolean = true

    @Mock
    private lateinit var dao: DogsDao

    private lateinit var repository: DogsRepository

    private lateinit var api: DogsApi

    @Before
    fun setUpTest() {
        MockitoAnnotations.initMocks(this)

        api = provideTestApiService(DogsApi::class.java)
        repository = DogsRepositoryImpl(api, dao, testCoroutineRule.dispatcherProvider, DogsDomainMapper(), DogsEntityMapper())
    }

    @Test
    fun `Send request to dogs api`() = runBlocking {
        whenever(dao.getCount()).thenReturn(0)

        mockHttpResponse("dogs_api_success.json", HttpURLConnection.HTTP_OK)

        whenever(dao.getAll()).thenReturn(
            flow {
                emit(
                    listOf(
                        DogEntity(
                            1,
                            "Rex",
                            "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
                            5,
                            "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
                        ),
                        DogEntity(
                            2,
                            "Spots",
                            "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
                            3,
                            "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
                        )
                    )
                )
            }
        )

        var response: Resource<List<Dog>>? = null

        repository
            .getDogs()
            .collect {
                response = it
            }

        assertTrue(response is Resource.Success)
        assertTrue((response as Resource.Success).value.isNotEmpty())
    }

    @Test
    fun `Send request to dogs api but server has an error`() = runBlocking {
        whenever(dao.getCount()).thenReturn(0)

        mockHttpResponse("dogs_api_error.json", HttpURLConnection.HTTP_INTERNAL_ERROR)

        var response: Resource<List<Dog>>? = null

        repository
            .getDogs()
            .collect {
                response = it
            }

        assertTrue(response is Resource.Failure)
        assertTrue((response as Resource.Failure).errorCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    fun `Check if app has information on database to retrieve data from database`() = runBlocking {
        whenever(dao.getCount()).thenReturn(2)
        whenever(dao.getAll()).thenReturn(
            flow {
                emit(
                    listOf(
                        DogEntity(
                            1,
                            "Rex",
                            "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
                            5,
                            "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
                        ),
                        DogEntity(
                            2,
                            "Spots",
                            "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
                            3,
                            "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
                        )
                    )
                )
            }
        )

        var response: Resource<List<Dog>>? = null

        repository
            .getDogs()
            .collect {
                response = it
            }

        assertTrue(response is Resource.Success)
        assertTrue((response as Resource.Success).value.isNotEmpty())
    }

}