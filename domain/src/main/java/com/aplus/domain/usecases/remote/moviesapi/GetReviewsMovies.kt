package com.aplus.domain.usecases.remote.moviesapi

import com.aplus.core.utils.ResponseResult
import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.repository.remote.MoviesApiRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetReviewsMovies @Inject constructor(
    private val repository: MoviesApiRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int) : Flow<ResponseResult<ReviewsResponse>> =
        repository.getReviews(movieId, page)
}