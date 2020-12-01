package com.raywenderlich.wewatch.search

import android.util.Log
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private val view: SearchContract.ViewInterface, private val dataSource: RemoteDataSource) : SearchContract.PresenterInterface {

    val TAG = "SearchPresenter"

    val searchResultsObservable: (String) -> Observable<TmdbResponse> = { query -> dataSource.searchResultsObservable(query) }

    val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {

            override fun onNext(@NonNull tmdbResponse: TmdbResponse) {
                Log.d(TAG, "OnNext" + tmdbResponse.totalResults)
                view.displayResult(tmdbResponse)
            }

            override fun onError(@NonNull e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
                view.displayError("Error fetching Movie Data")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    private val compositeDisposable = CompositeDisposable()

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }
}