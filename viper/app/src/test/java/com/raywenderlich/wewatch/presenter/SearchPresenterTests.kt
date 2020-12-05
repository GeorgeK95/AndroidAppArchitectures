package com.raywenderlich.wewatch.presenter

import com.raywenderlich.wewatch.AddContract
import com.raywenderlich.wewatch.SearchContract
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
class SearchPresenterTests {

    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var view: SearchContract.View

    @Mock
    private lateinit var interactor: SearchContract.Interactor

    @Test
    fun displayMovieListAndHideLoadingOnQuerySuccess() {
        val movieList = listOf<Movie>(Movie(title = "Home Alone"))
        presenter.onQuerySuccess(movieList)
        Mockito.verify(view).displayMovieList(movieList)
        Mockito.verify(view).hideLoading()
    }

    @Before
    fun setup() {
        val cicerone = Cicerone.create()
        val router = cicerone.router
        presenter = SearchPresenter(view, interactor, router)
    }
}