package com.aplus.feature.favorite.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aplus.core.utils.DispatcherProvider
import com.aplus.core.utils.NetworkHelper
import com.aplus.domain.model.Genres
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
import com.aplus.feature.favorite.MainCoroutineRule
import com.aplus.feature.favorite.TestDispatcherProvider
import com.aplus.feature.favorite.repository.remote.ApiMovieRepositoryFake
import com.aplus.feature.favorite.repository.local.GenresRepositoryFake
import com.aplus.feature.favorite.repository.local.MoviesRepositoryFake
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var apiMovieUseCases: ApiMovieUseCases
    private lateinit var genresUseCases: GenresUseCases
    private lateinit var moviesUseCases: MoviesUseCases
    private lateinit var networkHelper: NetworkHelper
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var viewModel: FavoriteViewModel
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
        viewModel = FavoriteViewModel(
            genresUseCases,
            moviesUseCases
        )
    }

    @Test
    fun `when getGenres is called observe with stateflow when get from local is success`() {
        runTest {
            assertEquals(
                fakeGenreRepository.dataGenres,
                viewModel.genres.value
            )
        }
    }

    @Test
    fun `when getGenres is called observe with stateflow when get from local is empty`() {
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
        viewModel = FavoriteViewModel(genresUseCases, moviesUseCases)

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
            viewModel.getFavorite()
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
        viewModel = FavoriteViewModel(genresUseCases, moviesUseCases)

        runTest {
            viewModel.getFavorite()
            assertEquals(
                emptyList<Movies>(),
                viewModel.favorit.value
            )
        }
    }

}