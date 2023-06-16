package com.aplus.domain.usecases.remote.moviesapi

import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.repository.remote.MoviesApiRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetReviewsMovies @Inject constructor(
    private val repository: MoviesApiRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int) : Flow<Response<ReviewsResponse>> =
        repository.getReviews(movieId, page)
}