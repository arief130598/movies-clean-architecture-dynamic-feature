package com.aplus.data.repository.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.aplus.data.MainCoroutineRule
import com.aplus.data.TestDispatcherProvider
import com.aplus.data.datasource.local.MovieDatabase
import com.aplus.domain.model.Movies
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesRepoImplTest {

    private val singleData = Movies(
        false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
        "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
        9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
        246
    )
    private val listData = mutableListOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
            "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663713, "en",
            "Terrifier 2", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )
    )
    private val duplicateListData = mutableListOf(
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
            "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        ),
        Movies(
            false, "/y5Z0WesTjvn59jP6yo459eUsbli.jpg", listOf(27,53), 663712, "en",
            "Terrifier 1", "After being resurrected by a sinister entity, Art the Clown returns to " +
                    "Miles County where he must hunt down and destroy a teenage girl and her younger brother on Halloween night.  " +
                    "As the body count rises, the siblings fight to stay alive while uncovering the true nature of Art's evil intent.",
            9049.191F,"/y5Z0WesTjvn59jP6yo459eUsbli.jpg", "2022-10-06", "Terrifier 2", 7.4F,
            246
        )
    )

    private lateinit var moviesRepoImpl : MoviesRepoImpl

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantTaskExecutorRules = InstantTaskExecutorRule()

    @Before
    fun setup() {
        moviesRepoImpl = mock()
    }

    @Test
    fun `test getSingle value with return is the inserted value`() = runTest {
        whenever(moviesRepoImpl.getSingle(1)).thenReturn(
            singleData
        )
        moviesRepoImpl.insertSingle(singleData)
        val result = moviesRepoImpl.getSingle(singleData.id)
        assertEquals(singleData, result)
    }

//    @Test
//    fun `test getSingle value with return is not the inserted value`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test getList with value is return the size insertedValue`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test getList without insert value is return empty`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test insertList with valid value is return total inserted value`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test insertList with duplicate value is return total distinct value`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test insertSingle with valid value is return 1`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test insertSingle with not valid value is return 0`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//
//    @Test
//    fun `test deleteAll with inserted value is return inserted value size`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test deleteSingle with inserted value is return 1`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
//
//    @Test
//    fun `test deleteSingle with different inserted value is return 0`() = runTest {
//        val data = ""
//        val result = moviesHelper.limitOverview(data)
//        assertEquals(data, result)
//    }
}