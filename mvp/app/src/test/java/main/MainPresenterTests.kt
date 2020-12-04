package main

import BaseTest
import RxImmediateSchedulerRule
import com.raywenderlich.wewatch.main.MainContract
import com.raywenderlich.wewatch.main.MainPresenter
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import kotlin.collections.ArrayList

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTests : BaseTest() {

    @Mock
    private lateinit var mockActivity: MainContract.ViewInterface

    @Mock
    private lateinit var mockDataSource: LocalDataSource

    lateinit var mainPresenter: MainPresenter

    private val dummyAllMovies: ArrayList<Movie>
        get() {
            val dummyMovieList = ArrayList<Movie>()
            dummyMovieList.add(Movie(title = "Title1", releaseDate = "ReleaseDate1", posterPath = "PosterPath1"))
            dummyMovieList.add(Movie(title = "Title2", releaseDate = "ReleaseDate2", posterPath = "PosterPath2"))
            dummyMovieList.add(Movie(title = "Title3", releaseDate = "ReleaseDate3", posterPath = "PosterPath3"))
            dummyMovieList.add(Movie(title = "Title4", releaseDate = "ReleaseDate4", posterPath = "PosterPath4"))
            return dummyMovieList
        }

    @Before
    fun setUp() {
        mainPresenter = MainPresenter(view = mockActivity, dataSource = mockDataSource)
    }

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val deletedHashSetSingle: HashSet<Movie>
        get() {
            return hashSetOf(dummyAllMovies[2])
        }

    private val deletedHashSetMultiple: HashSet<Movie>
        get() {
            return hashSetOf(dummyAllMovies[1], dummyAllMovies[3])
        }

    @Test
    fun testDeleteMultiple() {
        val myDeletedHashSet = deletedHashSetMultiple
        mainPresenter.onDeleteTapped(myDeletedHashSet)

        myDeletedHashSet.forEach {
            Mockito.verify(mockDataSource).delete(it)
        }

        Mockito.verify(mockActivity).displayMessage("Movies deleted")
    }

    @Test
    fun testDeleteSingle() {
        val myDeletedHashSet = deletedHashSetSingle
        mainPresenter.onDeleteTapped(myDeletedHashSet)

        myDeletedHashSet.forEach {
            Mockito.verify(mockDataSource).delete(it)
        }

        Mockito.verify(mockActivity).displayMessage("Movie deleted")
    }

    @Test
    fun testGetMyMoviesListWithNoMovies() {
//        Mockito.doReturn(Observable.just(ArrayList<Movie>())).`when`(mockDataSource.allMovies)
        Mockito.`when`(mockDataSource.allMovies).thenReturn(Observable.just(emptyList()))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayNoMovies()
    }

    @Test
    fun testGetMyMoviesList() {
        val myDummyMovies = dummyAllMovies

        Mockito.`when`(mockDataSource.allMovies).thenReturn(Observable.just(myDummyMovies))

        mainPresenter.getMyMoviesList()

        Mockito.verify(mockDataSource).allMovies
        Mockito.verify(mockActivity).displayMovies(myDummyMovies)
    }
}