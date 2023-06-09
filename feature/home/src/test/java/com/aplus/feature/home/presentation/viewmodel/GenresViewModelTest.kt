package com.aplus.feature.home.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.core.utils.Resource
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.usecases.local.genres.DeleteAllGenres
import com.aplus.domain.usecases.local.genres.DeleteSingleGenres
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.genres.GetListGenres
import com.aplus.domain.usecases.local.genres.GetSingleGenres
import com.aplus.domain.usecases.local.genres.InsertListGenres
import com.aplus.domain.usecases.local.genres.InsertSingleGenres
import com.aplus.domain.usecases.remote.apimovie.ApiMovieUseCases
import com.aplus.domain.usecases.remote.apimovie.GetGenresApi
import com.aplus.domain.usecases.remote.apimovie.GetMoviesApi
import com.aplus.domain.usecases.remote.apimovie.GetNowPlayingApi
import com.aplus.domain.usecases.remote.apimovie.GetPopularApi
import com.aplus.domain.usecases.remote.apimovie.GetReviewsApi
import com.aplus.domain.usecases.remote.apimovie.GetSearchApi
import com.aplus.domain.usecases.remote.apimovie.GetSimilarApi
import com.aplus.domain.usecases.remote.apimovie.GetUpcomingApi
import com.aplus.domain.usecases.remote.apimovie.GetVideosApi
import com.aplus.feature.home.MainCoroutineRule
import com.aplus.feature.home.TestDispatcherProvider
import com.aplus.feature.home.repository.local.ApiMovieRepositoryFake
import com.aplus.feature.home.repository.remote.GenresRepositoryFake
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GenresViewModelTest {

    private lateinit var apiMovieUseCases: ApiMovieUseCases
    private lateinit var genresUseCases: GenresUseCases
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: GenresViewModel
    private lateinit var fakeApiMovieRepository: ApiMovieRepositoryFake
    private lateinit var fakeGenreRepository: GenresRepositoryFake

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRules = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup(){
        networkHelper = mock()
        dispatcherProvider = TestDispatcherProvider()
        fakeApiMovieRepository = ApiMovieRepositoryFake()
        fakeGenreRepository = GenresRepositoryFake()
        genresUseCases = GenresUseCases(
            deleteAllGenres = DeleteAllGenres(fakeGenreRepository),
            deleteSingleGenres = DeleteSingleGenres(fakeGenreRepository),
            getListGenres = GetListGenres(fakeGenreRepository),
            getSingleGenres = GetSingleGenres(fakeGenreRepository),
            insertListGenres = InsertListGenres(fakeGenreRepository),
            insertSingleGenres = InsertSingleGenres(fakeGenreRepository)
        )
        apiMovieUseCases = ApiMovieUseCases(
            getGenresApi = GetGenresApi(fakeApiMovieRepository),
            getMoviesApi = GetMoviesApi(fakeApiMovieRepository),
            getNowPlayingApi = GetNowPlayingApi(fakeApiMovieRepository),
            getPopularApi = GetPopularApi(fakeApiMovieRepository),
            getSearchApi = GetSearchApi(fakeApiMovieRepository),
            getSimilarApi = GetSimilarApi(fakeApiMovieRepository),
            getUpcomingApi = GetUpcomingApi(fakeApiMovieRepository),
            getVideosApi = GetVideosApi(fakeApiMovieRepository),
            getReviewsApi = GetReviewsApi(fakeApiMovieRepository)
        )

        runBlocking {
            whenever(networkHelper.isNetworkConnected()).thenReturn(true)
        }

        viewModel = GenresViewModel(apiMovieUseCases, genresUseCases, networkHelper, dispatcherProvider)
    }

    @Test
    fun `when getGenres is called observe with stateflow when api is success`() {
        runTest {
            Assert.assertEquals(
                Resource.success(fakeApiMovieRepository.dataGenres.body()!!.genres),
                viewModel.genres.value
            )
        }
    }

    @Test
    fun `when getGenres is called observe with stateflow when api is failed`() {
        val errorResponseGenres = "{ Unauthorized }".toResponseBody("application/json".toMediaTypeOrNull())
        val errorResourceGenres = Response.error<GenresResponse>(401, errorResponseGenres)

        fakeApiMovieRepository.dataGenres = errorResourceGenres
        apiMovieUseCases = ApiMovieUseCases(
            getGenresApi = GetGenresApi(fakeApiMovieRepository),
            getMoviesApi = GetMoviesApi(fakeApiMovieRepository),
            getNowPlayingApi = GetNowPlayingApi(fakeApiMovieRepository),
            getPopularApi = GetPopularApi(fakeApiMovieRepository),
            getSearchApi = GetSearchApi(fakeApiMovieRepository),
            getSimilarApi = GetSimilarApi(fakeApiMovieRepository),
            getUpcomingApi = GetUpcomingApi(fakeApiMovieRepository),
            getVideosApi = GetVideosApi(fakeApiMovieRepository),
            getReviewsApi = GetReviewsApi(fakeApiMovieRepository)
        )
        viewModel = GenresViewModel(apiMovieUseCases, genresUseCases, networkHelper, dispatcherProvider)

        runTest {
            Assert.assertEquals(
                Resource.error(errorResourceGenres.message(), null),
                viewModel.genres.value
            )
        }
    }
}