package com.raywenderlich.wewatch.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.wewatch.data.MovieRepository
import com.raywenderlich.wewatch.data.MovieRepositoryImpl
import com.raywenderlich.wewatch.data.model.Movie

class AddViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) : ViewModel() {

    var title = ObservableField("")
    var releaseDate = ObservableField("")

    private val saveLiveData = MutableLiveData<Boolean>()

    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData

    fun saveMovie() {
        if (canSaveMovie()) {
            repository.saveMovie(Movie(title = title.get(), releaseDate = releaseDate.get()))
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

    private fun canSaveMovie(): Boolean {
        this.title.get()?.let {
            return it.isNotEmpty()
        }
        return false
    }

}