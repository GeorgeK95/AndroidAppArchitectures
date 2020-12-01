package com.raywenderlich.wewatch.add

import android.text.TextUtils
import com.raywenderlich.wewatch.main.MainContract
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie

class AddMoviePresenter(private val view: AddMovieContact.ViewInterface, private val dataSource: LocalDataSource) : AddMovieContact.PresenterInterface {

    override fun addMovie(title: String, releaseDate: String, posterPath: String) {
        if (TextUtils.isEmpty(title)) {
            view.displayError("Movie title cannot be empty")
        } else {
            val movie = Movie(title = title, releaseDate = releaseDate, posterPath = posterPath)
            dataSource.insert(movie)
            view.returnToMain()
        }
    }

}