package com.raywenderlich.wewatch.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.wewatch.data.MovieRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTests {

    private lateinit var addViewModel: AddViewModel

    @Mock
    lateinit var repository: MovieRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun isMovieProperlySaved() {
        addViewModel.title.set("Awesome Movie II")
        addViewModel.releaseDate.set("1994")
        addViewModel.saveMovie()
        assertEquals(true, addViewModel.getSaveLiveData().value)
    }

    @Test
    fun cantSaveMovieWithoutDate() {
        addViewModel.title.set("Awesome Movie I")
        addViewModel.releaseDate.set("")
        val canSaveMovie = addViewModel.canSaveMovie()
        assertEquals(false, canSaveMovie)
    }

    @Test
    fun cantSaveMovieWithoutTitle() {
        addViewModel.title.set("")
        addViewModel.releaseDate.set("")

        val canSaveMovie = addViewModel.canSaveMovie()

        assertEquals(false, canSaveMovie)
    }

    @Before
    fun setup() {
        addViewModel = AddViewModel(repository)
    }

}