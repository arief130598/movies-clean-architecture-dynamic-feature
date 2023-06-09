package com.aplus.common.utils

import com.aplus.domain.model.Genres
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesHelperTest{

    private lateinit var moviesHelper: MoviesHelper

    private val genresData = listOf(
        Genres(1, "Action"),
        Genres(2, "Horror"),
        Genres(3, "Science"),
        Genres(4, "Romance")
    )

    @Before
    fun setup(){
        moviesHelper = MoviesHelper()
    }

    @Test
    fun `test limitOverview() with empty value is return empty`(){
        val data = ""
        val result = moviesHelper.limitOverview(data)
        assertEquals(data, result)
    }

    @Test
    fun `test limitOverview() with string less than 100 chars is return value`(){
        val data = "After being resurrected by a sinister entity, Art the Clown returns"
        val result = moviesHelper.limitOverview(data)
        assertEquals(data, result)
    }

    @Test
    fun `test limitOverview() with string more than 100 chars is return 103 chars value`(){
        val data = "After being resurrected by a sinister entity, Art the Clown returns to " +
                "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent."
        val result = moviesHelper.limitOverview(data)
        assertEquals(103, result.length)
    }

    @Test
    fun `test convertGenres() with empty list is return empty string`() {
        val data = listOf<Int>()
        val result = moviesHelper.convertGenres(data, genresData)
        assertEquals("", result)
    }

    @Test
    fun `test convertGenres() with list that containing data is return genres name`() {
        val data = listOf(1,2)
        val result = moviesHelper.convertGenres(data, genresData)
        assertEquals("Action, Horror", result)
    }

    @Test
    fun `test convertGenres() with list that containing data and not is return genres name with genres id`() {
        val data = listOf(0,2)
        val result = moviesHelper.convertGenres(data, genresData)
        assertEquals("0, Horror", result)
    }

    @Test
    fun `test convertGenres() with list that not containing data is return genres id`() {
        val data = listOf(7,8)
        val result = moviesHelper.convertGenres(data, genresData)
        assertEquals("7, 8", result)
    }
}