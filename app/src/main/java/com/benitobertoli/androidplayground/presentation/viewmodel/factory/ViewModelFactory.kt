package com.benitobertoli.androidplayground.presentation.viewmodel.factory

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory {

    fun <T : ViewModel> getViewModel(
        fragment: Fragment,
        provideViewModel: () -> T,
        viewModelClass: Class<T>
    ): T {
        return ViewModelProvider(fragment, Factory(provideViewModel))[viewModelClass]
    }

    private class Factory<T : ViewModel>(
        private val createViewModel: () -> T
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return createViewModel() as T
        }
    }

}

inline fun <reified T : ViewModel> Fragment.getViewModel(noinline provideViewModel: () -> T): T {
    return ViewModelFactory().getViewModel(this, provideViewModel, T::class.java)
}