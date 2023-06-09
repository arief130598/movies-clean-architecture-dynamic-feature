package com.aplus.domain.usecases.remote.apimovie

import com.aplus.domain.model.ReviewsResponse
import com.aplus.domain.repository.remote.ApiMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetReviewsApi @Inject constructor(
    private val repository: ApiMovieRepository
) {
    suspend operator fun invoke(movieId: Int, page: Int) : Flow<Response<ReviewsResponse>> =
        repository.getReviews(movieId, page)
}