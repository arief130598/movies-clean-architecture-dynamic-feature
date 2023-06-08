package com.aplus.common.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aplus.common.MainCoroutineRule
import com.aplus.common.TestDispatcherProvider
import com.aplus.common.repository.local.GenresRepositoryFake
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.domain.model.Genres
import com.aplus.domain.model.GenresResponse
import com.aplus.domain.model.Movies
import com.aplus.domain.usecases.local.genres.DeleteAllGenres
import com.aplus.domain.usecases.local.genres.DeleteSingleGenres
import com.aplus.domain.usecases.local.genres.GenresUseCases
import com.aplus.domain.usecases.local.genres.GetListGenres
import com.aplus.domain.usecases.local.genres.GetSingleGenres
import com.aplus.domain.usecases.local.genres.InsertListGenres
import com.aplus.domain.usecases.local.genres.InsertSingleGenres
import com.aplus.domain.usecases.local.movies.DeleteAllMovies
import com.aplus.domain.usecases.local.movies.DeleteSingleMovies
import com.aplus.domain.usecases.local.movies.GetListMovies
import com.aplus.domain.usecases.local.movies.GetSingleMovies
import com.aplus.domain.usecases.local.movies.InsertListMovies
import com.aplus.domain.usecases.local.movies.InsertSingleMovies
import com.aplus.domain.usecases.local.movies.MoviesUseCases
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
import com.aplus.common.repository.local.MoviesRepositoryFake
import com.aplus.common.repository.remote.ApiMovieRepositoryFake
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var apiMovieUseCases: ApiMovieUseCases
    private lateinit var genresUseCases: GenresUseCases
    private lateinit var moviesUseCases: MoviesUseCases
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: FakeMovieViewModel
    private lateinit var fakeApiMovieRepository: ApiMovieRepositoryFake
    private lateinit var fakeGenreRepository: GenresRepositoryFake
    private lateinit var fakeMoviesRepository: MoviesRepositoryFake

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRules = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        networkHelper = mock()
        dispatcherProvider = TestDispatcherProvider()
        fakeApiMovieRepository = ApiMovieRepositoryFake()
        fakeGenreRepository = GenresRepositoryFake()
        fakeMoviesRepository = MoviesRepositoryFake()

        genresUseCases = GenresUseCases(
            deleteAllGenres = DeleteAllGenres(fakeGenreRepository),
            deleteSingleGenres = DeleteSingleGenres(fakeGenreRepository),
            getListGenres = GetListGenres(fakeGenreRepository),
            getSingleGenres = GetSingleGenres(fakeGenreRepository),
            insertListGenres = InsertListGenres(fakeGenreRepository),
            insertSingleGenres = InsertSingleGenres(fakeGenreRepository)
        )
        moviesUseCases = MoviesUseCases(
            deleteAllMovies = DeleteAllMovies(fakeMoviesRepository),
            deleteSingleMovies = DeleteSingleMovies(fakeMoviesRepository),
            getListMovies = GetListMovies(fakeMoviesRepository),
            getSingleMovies = GetSingleMovies(fakeMoviesRepository),
            insertListMovies = InsertListMovies(fakeMoviesRepository),
            insertSingleMovies = InsertSingleMovies(fakeMoviesRepository)
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

        viewModel = FakeMovieViewModel(
            apiMovieUseCases,
            genresUseCases,
            moviesUseCases,
            dispatcherProvider
        )
    }

    @Test
    fun `when getGenres is called observe with stateflow when get from local or remote is success`() {
        runTest {
            assertEquals(
                fakeGenreRepository.dataGenres,
                viewModel.genres.value
            )
        }
    }

    @Test
    fun `when getGenres is called observe with stateflow when get from local is empty and remote is failed`() {
        fakeGenreRepository = GenresRepositoryFake()
        fakeGenreRepository.dataGenres = listOf()

        genresUseCases = GenresUseCases(
            deleteAllGenres = DeleteAllGenres(fakeGenreRepository),
            deleteSingleGenres = DeleteSingleGenres(fakeGenreRepository),
            getListGenres = GetListGenres(fakeGenreRepository),
            getSingleGenres = GetSingleGenres(fakeGenreRepository),
            insertListGenres = InsertListGenres(fakeGenreRepository),
            insertSingleGenres = InsertSingleGenres(fakeGenreRepository)
        )

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

        viewModel = FakeMovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcherProvider)

        runTest {
            assertEquals(
                emptyList<Genres>(),
                viewModel.genres.value
            )
        }
    }

    @Test
    fun `when getFavorite is called observe with stateflow when get from local is success`() {
        runTest {
            assertEquals(
                fakeMoviesRepository.listMovies,
                viewModel.favorit.value
            )
        }
    }

    @Test
    fun `when getFavorite is called observe with stateflow when get from local is empty`() {
        fakeMoviesRepository = MoviesRepositoryFake()
        fakeMoviesRepository.listMovies = listOf()

        moviesUseCases = MoviesUseCases(
            deleteAllMovies = DeleteAllMovies(fakeMoviesRepository),
            deleteSingleMovies = DeleteSingleMovies(fakeMoviesRepository),
            getListMovies = GetListMovies(fakeMoviesRepository),
            getSingleMovies = GetSingleMovies(fakeMoviesRepository),
            insertListMovies = InsertListMovies(fakeMoviesRepository),
            insertSingleMovies = InsertSingleMovies(fakeMoviesRepository)
        )
        viewModel = FakeMovieViewModel(apiMovieUseCases, genresUseCases, moviesUseCases, dispatcherProvider)

        runTest {
            assertEquals(
                emptyList<Movies>(),
                viewModel.favorit.value
            )
        }
    }
}