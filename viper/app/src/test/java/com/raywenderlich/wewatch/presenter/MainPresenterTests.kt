package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.MainContract
import com.raywenderlich.wewatch.data.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import ru.terrakok.cicerone.Cicerone

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests {

    private lateinit var presenter: MainPresenter

    @Mock
    private lateinit var view: MainContract.View

    @Mock
    private lateinit var interactor: MainContract.Interactor

    @Test
    fun loadMovies() {
        presenter.onViewCreated()
        Mockito.verify(interactor).loadMovieList()
    }

    @Test
    fun displayMoviesOnQuerySuccess() {
        val movies = listOf<Movie>(Movie(title = "Home Alone", releaseDate = "1990"))
        presenter.onQuerySuccess(movies)
        Mockito.verify(view).displayMovieList(movies)
    }

    @Before
    fun setup() {
        val cicerone = Cicerone.create()
        presenter = MainPresenter(view, interactor, cicerone.router)
    }
}