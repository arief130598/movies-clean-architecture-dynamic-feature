package com.aplus.feature.home.presentation.navigation

import com.aplus.core.constants.DeeplinkConstant
import com.aplus.core.constants.KeyConstant
import com.aplus.feature.home.presentation.ui.GenresFragment

fun GenresFragment.gotoChosenGenres(genres: String) {
    nav.navigateDeeplink(
        requireParentFragment(),
        DeeplinkConstant.GENRES_MOVIES_NAVIGATION,
        Pair(KeyConstant.GENRES_KEY, genres)
    )
}