package com.raywenderlich.wewatch.interactor

import com.raywenderlich.wewatch.AddContract
import com.raywenderlich.wewatch.data.MovieRepository
import com.raywenderlich.wewatch.data.MovieRepositoryImpl
import com.raywenderlich.wewatch.data.entity.Movie

class AddMovieInteractor(
        private val repository: MovieRepository = MovieRepositoryImpl()
) : AddContract.Interactor {

    override fun addMovie(movie: Movie) = repository.saveMovie(movie)

}