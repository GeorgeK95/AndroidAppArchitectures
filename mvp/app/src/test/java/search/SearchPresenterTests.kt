package search

import BaseTest
import RxImmediateSchedulerRule
import com.nhaarman.mockito_kotlin.any
import com.raywenderlich.wewatch.model.Movie
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import com.raywenderlich.wewatch.search.SearchContract
import com.raywenderlich.wewatch.search.SearchPresenter
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchPresenterTests : BaseTest() {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var mockActivity: SearchContract.ViewInterface

    @Mock
    private val mockDataSource = RemoteDataSource()

    lateinit var searchPresenter: SearchPresenter

    private val dummyResponse: TmdbResponse
        get() {
            val dummyMovieList = ArrayList<Movie>()
            dummyMovieList.add(Movie(title = "Title1", releaseDate = "ReleaseDate1", posterPath = "PosterPath1"))
            dummyMovieList.add(Movie(title = "Title2", releaseDate = "ReleaseDate2", posterPath = "PosterPath2"))
            dummyMovieList.add(Movie(title = "Title3", releaseDate = "ReleaseDate3", posterPath = "PosterPath3"))
            dummyMovieList.add(Movie(title = "Title4", releaseDate = "ReleaseDate4", posterPath = "PosterPath4"))
            return TmdbResponse(1, 4, 5, dummyMovieList)
        }

    @Test
    fun testSearchMovieError() {
        Mockito.`when`(mockDataSource.searchResultsObservable(anyString())).thenReturn(Observable.error(Throwable("Something went wrong")))

        searchPresenter.getSearchResults("pesho")

        Mockito.verify(mockActivity).displayError("Error fetching Movie Data")
    }

    @Test
    fun testSearchMovie() {
        val myDummyResponse = dummyResponse

        Mockito.`when`(mockDataSource.searchResultsObservable(anyString())).thenReturn(Observable.just(myDummyResponse))

        searchPresenter.getSearchResults("pesho")

        Mockito.verify(mockActivity).displayResult(myDummyResponse)
    }

    @Before
    fun setUp() {
        searchPresenter = SearchPresenter(view = mockActivity, dataSource = mockDataSource)
    }
}