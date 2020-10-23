package roxwin.tun.baseui.base

import androidx.databinding.BaseObservable
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val disposable by lazy { CompositeDisposable() }
    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}