package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.AddContract
import com.raywenderlich.wewatch.data.entity.Movie
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Cicerone

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTests {
    private lateinit var presenter: AddMoviePresenter

    @Mock
    private lateinit var view: AddContract.View

    @Mock
    private lateinit var interactor: AddContract.Interactor

    @Test
    fun addMovieWithTitle() {
        val movie = Movie(title = "Home Alone", releaseDate = "1990")
        presenter.addMovies(movie.title!!, movie.releaseDate!!)
        Mockito.verify(interactor).addMovie(movie)
    }

    @Before
    fun setup() {
        val cicerone = Cicerone.create()
        val router = cicerone.router
        presenter = AddMoviePresenter(view, interactor, router)
    }
}